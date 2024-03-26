package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Car;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class CarForSell extends Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carSellId;
    private double price;
    @ManyToOne()
    @JoinColumn(name="agencyId")
    private Agency agency;

}
