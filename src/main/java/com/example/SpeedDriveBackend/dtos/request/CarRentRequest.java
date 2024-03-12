package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.UUID;

@Data
public class CarRentRequest {
    private long carRentId;
    private String make;
    private String model;
    private int manifacturingYear;

    private fuelType fuel;
    private double rentalPrice;
    private UUID agencyId;

}
