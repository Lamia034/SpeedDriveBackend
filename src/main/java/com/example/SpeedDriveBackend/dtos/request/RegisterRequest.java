package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.enumerations.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;

}
