package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import com.example.SpeedDriveBackend.enumerations.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class ClientResponse {
    protected String name;
    protected String email;
    protected String password;
    @Enumerated(EnumType.STRING)
    protected Role role;
    private List<CarForRent> CarsForRent;
    private List<CarForSell> CarsForSell;
}
