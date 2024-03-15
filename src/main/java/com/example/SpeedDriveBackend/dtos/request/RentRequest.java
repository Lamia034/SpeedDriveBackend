package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.entities.Client;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RentRequest {
    private RentId id;
    private UUID clientId;
    private Long carRentId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
