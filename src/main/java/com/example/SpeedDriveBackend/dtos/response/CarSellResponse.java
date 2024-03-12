package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import lombok.Data;

@Data
public class CarSellResponse {
    private long carSellId;
    private String make;
    private String model;
    private int manifacturingYear;
    private fuelType fuel;
    private double rentalPrice;
    private Agency agency;
}
