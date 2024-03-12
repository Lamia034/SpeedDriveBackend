package com.example.SpeedDriveBackend.entities.abstracts;

import com.example.SpeedDriveBackend.enumerations.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Data
@MappedSuperclass
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person implements UserDetails{
    protected String name;
    protected String email;
    protected String password;
    @Enumerated(EnumType.STRING)
    protected Role role;


}
