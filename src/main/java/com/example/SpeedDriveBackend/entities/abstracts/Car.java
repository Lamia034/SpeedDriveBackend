package com.example.SpeedDriveBackend.entities.abstracts;

import com.example.SpeedDriveBackend.enumerations.fuelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
@Data
@MappedSuperclass
public abstract class Car implements Serializable {
    protected String make;
    protected String model;
    protected int manifacturingYear;
    @Enumerated(EnumType.STRING)
    protected fuelType fuel;
}
