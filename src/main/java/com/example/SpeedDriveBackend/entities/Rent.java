package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.embeddable.RentId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rent {
    @EmbeddedId
    private RentId id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client client;
    @ManyToOne
    @MapsId("carRentId")
    @JoinColumn(name = "carRentId", referencedColumnName = "carRentId")
    private CarForRent carForRent;
}
