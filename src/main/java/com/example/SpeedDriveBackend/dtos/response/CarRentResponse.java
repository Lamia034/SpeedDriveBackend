package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.enumerations.Role;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties({"carForRent"})

public class CarRentResponse {
    private long carRentId;
    private String make;
    private String model;
    private int manifacturingYear;

    private fuelType fuel;
    private double rentalPrice;
//    private AgencyRequest agency;
    private List<RentRequest> rents;
//    private ClientRequest client;

    protected String name;
    protected String email;
}
