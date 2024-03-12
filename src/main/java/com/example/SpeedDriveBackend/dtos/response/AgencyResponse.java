package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import com.example.SpeedDriveBackend.enumerations.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class AgencyResponse {
    private UUID agencyId;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String adress;
//    @OneToMany(mappedBy = "agency" ,cascade = CascadeType.ALL)
    private List<CarForRent> CarsForRent;
//    @OneToMany(mappedBy = "agency" ,cascade = CascadeType.ALL)
    private List<CarForSell> CarsForSell;
}
