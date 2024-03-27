package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

import static com.example.SpeedDriveBackend.enumerations.CarStatus.AVAILABLE;

@Data
public class CarRentRequest {
    private long carRentId;
    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private int manifacturingYear;
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus ;
//    private MultipartFile imageFile;
@Column(length = 100000)
private String imagePath;
    @NotNull
    private fuelType fuel;
    @NotNull
    private double rentalPrice;
    private UUID agencyId;

}
