package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@ToString
//@Inheritance
public class Agency extends Person {
    @Id
    private UUID agencyId;
    private String adress;
//    @OneToMany(mappedBy = "agency" , fetch = FetchType.EAGER , cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rent> rents;
    @OneToMany(mappedBy = "agency" , fetch = FetchType.EAGER , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CarForRent> carsForRent;
    @OneToMany(mappedBy = "agency" , fetch = FetchType.EAGER , cascade = CascadeType.ALL ,orphanRemoval = true)
    private List<CarForSell> carsForSell;
    @OneToMany(mappedBy = "agency", fetch = FetchType.EAGER)//gere par le champ carforrent dan rents
    @JsonIgnore
    private List<ChatRoom> chatRooms;

    @Override
    public String toString() {
        return "Agency [agencyId=" + agencyId + ", address=" + adress + "]";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
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
