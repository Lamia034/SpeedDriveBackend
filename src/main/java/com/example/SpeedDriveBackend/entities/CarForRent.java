package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


//@Inheritance

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CarForRent extends Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carRentId;
    private double rentalPrice;
    @ManyToOne()
    @MapsId("agencyId")
    @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
    private Agency agency;

//    @OneToMany(mappedBy = "carForRent")
//
//    private List<Rent> rents;



    //    @Transient
//    private String image;
//
//    public void setImageFile(MultipartFile imageFile) {
//        this.imageFile = imageFile;
//        setImageFile(imageFile != null ? imageFile.getOriginalFilename() : null);
//    }
//    @JsonIgnore
}
