package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.enumerations.fuelType;
import lombok.Data;

import java.util.UUID;

@Data
public class CarSellRequest {
    private long carSellId;
    private String make;
    private String model;
    private int manifacturingYear;
    private fuelType fuel;
    private double rentalPrice;
    private UUID agencyId;
}
