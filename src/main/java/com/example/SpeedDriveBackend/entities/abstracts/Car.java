package com.example.SpeedDriveBackend.entities.abstracts;

import com.example.SpeedDriveBackend.enumerations.fuelType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
@Data
@MappedSuperclass
public abstract class Car implements Serializable {
    protected String make;
    protected String model;
    protected int manifacturingYear;
    @Enumerated(EnumType.STRING)
    protected fuelType fuel;
    @Column(name = "imagePath")
    private String imagePath;
//    @Lob
//    @Column(name = "image", columnDefinition="BLOB")
//
//    @Lob
//    private byte[] image;
}
