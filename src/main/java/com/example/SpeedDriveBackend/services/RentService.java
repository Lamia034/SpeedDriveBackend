package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.embeddable.RentId;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RentService {
    RentResponse addRent(RentRequest RentRequest);

    List<RentResponse> getRentsByAgency(UUID agencyId , Pageable pageable);
    List<RentResponse> getRentsByClient(UUID clientId , Pageable pageable);
   void deleteRentById(UUID clientId, Long carRentId);

}
