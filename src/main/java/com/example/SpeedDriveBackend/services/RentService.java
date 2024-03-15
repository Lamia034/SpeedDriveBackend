package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;

public interface RentService {
    RentResponse addRent(RentRequest RentRequest);

}
