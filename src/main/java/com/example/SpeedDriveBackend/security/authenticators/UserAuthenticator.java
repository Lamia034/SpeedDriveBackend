
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//@AllArgsConstructor
//public class UserAuthenticator implements UserDetails {
//
//    private final Member member;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        System.out.println(member.getRole().name());
//        return Collections.singleton(new SimpleGrantedAuthority(member.getRole().name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getEmail();
//    }
//
//    public Integer getUserId() {
//        return member.getNum();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
