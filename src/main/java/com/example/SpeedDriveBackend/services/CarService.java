package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarRentResponse addCarForRent(CarRentRequest carRentRequest);

    List<CarRentResponse> getAllCarsForRent(Pageable pageable);
    List<CarRentResponse> getCarsForRentByAgency(UUID agencyId , Pageable pageable);
//    CarRentResponse updateCarForRent(Long carRentId, CarRentRequest carRentRequest);
    CarRentResponse updateCarForRent(long carRentId, CarRentRequest carRentRequest);
    CarSellResponse addCarForSell(CarSellRequest carSellRequest);

boolean deleteCarForRentById(Long carRentId);


}
