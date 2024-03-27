package com.example.SpeedDriveBackend.security;

import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.entities.Client;
import com.example.SpeedDriveBackend.entities.abstracts.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@Setter
@Getter
public class JwtService {

    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    private String token;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        System.out.println(claims);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateToken(UserDetails userDetails) {
        if (!(userDetails instanceof Person)) {
            throw new IllegalArgumentException("Unsupported user type: " + userDetails.getClass());
        }

        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, (Person) userDetails);
    }



    public String createToken(Map<String, Object> claims, Person person) {
        String idClaimKey = "";
        String roleClaimKey = "role";

        if (person instanceof Agency) {
            idClaimKey = "agencyId";
            claims.put(roleClaimKey, "AGENCY");
        } else if (person instanceof Client) {
            idClaimKey = "clientId";
            claims.put(roleClaimKey, "CLIENT");
        }

        System.out.println("subject: "+ person.getUsername());

        return Jwts.builder()
                .setClaims(claims)

                .setSubject(person.getEmail())
                .claim("name", person.getName())
                .claim(idClaimKey, extractIdFromPerson(person))
                .setIssuer(person.getAuthorities().toString().substring(1, person.getAuthorities().toString().length() - 1))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


    private UUID extractIdFromPerson(Person person) {
        if (person instanceof Agency) {
            return ((Agency) person).getAgencyId();
        } else if (person instanceof Client) {
            return ((Client) person).getClientId();
        } else {
            throw new IllegalArgumentException("Unsupported person type: " + person.getClass());
        }
    }



    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }


//    public String extractUserRoleFromToken() {
//        Claims claims = Jwts.parser()
//                .setSigningKey(getSignKey())
//                .parseClaimsJws(getToken())
//                .getBody();
//        return (String) claims.get("role");
//    }
}
