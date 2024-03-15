package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface CarService {
    CarRentResponse addCarForRent(CarRentRequest carRentRequest);
//CarRentResponse addCarForRent(CarRentRequest carRentRequest, MultipartFile imageFile);

    List<CarRentResponse> getAllCarsForRent(Pageable pageable);
    List<CarRentResponse> getCarsForRentByAgency(UUID agencyId , Pageable pageable);
//    CarRentResponse updateCarForRent(Long carRentId, CarRentRequest carRentRequest);
    CarRentResponse updateCarForRent(long carRentId, CarRentRequest carRentRequest);
    CarSellResponse addCarForSell(CarSellRequest carSellRequest);
    CarRentResponse getCarForRentById(Long carRentId);
boolean deleteCarForRentById(Long carRentId);


}
