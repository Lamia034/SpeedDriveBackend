package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.embeddable.RentId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Rent {
    @EmbeddedId
    private RentId id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "clientId", referencedColumnName = "clientId", insertable = false)
    private Client client;
    @ManyToOne
    @MapsId("carRentId")
    @JoinColumn(name = "carRentId", referencedColumnName = "carRentId", insertable = false)
    private CarForRent carForRent;
}
