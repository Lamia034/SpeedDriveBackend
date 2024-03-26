package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@ToString
//@Inheritance
public class Client extends Person {
    @Id
    private UUID clientId;

@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)//gere par le champ carforrent dan rents
@JsonIgnore
private List<Rent> rents;
//    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
//    private List<CarForSell> carsForSell;
@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)//gere par le champ carforrent dan rents
@JsonIgnore
private List<ChatRoom> chatRooms;

    @Override
    public String toString() {
        return "Client [clientId=" + clientId + "]";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(super.getRole().name())); // Assuming getRole() method exists in Person
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
