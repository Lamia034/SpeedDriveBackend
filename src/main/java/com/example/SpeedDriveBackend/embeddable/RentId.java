package com.example.SpeedDriveBackend.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class RentId implements Serializable {
    @Column(name = "clientId")
    private UUID clientId;
    @Column(name = "carRentId")
    private long carRentId;
}
