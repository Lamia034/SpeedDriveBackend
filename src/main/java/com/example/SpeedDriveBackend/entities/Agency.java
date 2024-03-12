package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
//@Inheritance
public class Agency extends Person {
    @Id
    private UUID agencyId;
    private String adress;
    @OneToMany(mappedBy = "agency" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<CarForRent> carsForRent;
    @OneToMany(mappedBy = "agency" , fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<CarForSell> carsForSell;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
