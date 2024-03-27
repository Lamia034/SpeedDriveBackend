package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.embeddable.RentId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rent {
    @EmbeddedId
    private RentId id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID agencyId;
    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "clientId", referencedColumnName = "clientId")
    private Client client;
    @ManyToOne
    @MapsId("carRentId")
    @JoinColumn(name = "carRentId", referencedColumnName = "carRentId")
    private CarForRent carForRent;


    @Override
    public String toString() {
        return "Rent [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", agencyId=" + agencyId + "]";
    }
}
