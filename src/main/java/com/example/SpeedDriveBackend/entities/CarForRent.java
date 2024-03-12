package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Car;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="agencyId")
    private Agency agency;

//    @JsonIgnore
//    @OneToMany(mappedBy = "carForRent")
//
//    private List<Rent> rents;
}
