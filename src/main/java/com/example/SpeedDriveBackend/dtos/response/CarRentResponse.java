package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.example.SpeedDriveBackend.enumerations.Role;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Data
//@JsonIgnoreProperties({"carForRent"})

public class CarRentResponse {
    private long carRentId;
    private String make;
    private String model;
    private int manifacturingYear;
//    private MultipartFile imageFile;
    private String imagePath;
    private fuelType fuel;
    private double rentalPrice;
    private CarStatus carStatus;
    private UUID agencyId;
    protected String name;
    protected String email;
    //    private AgencyResponse agency;

//    private ClientRequest client;

//@JsonIgnore
//    private List<RentRequest> rents;
}
