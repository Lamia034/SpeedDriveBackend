package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.entities.Client;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class RentResponse {
    private RentId id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Client client;
    private CarRentRequest carForRent;
}
