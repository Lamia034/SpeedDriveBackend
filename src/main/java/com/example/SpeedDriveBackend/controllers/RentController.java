package com.example.SpeedDriveBackend.controllers;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.services.CarService;
import com.example.SpeedDriveBackend.services.RentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/rent")
@RequiredArgsConstructor


public class RentController {


    private final RentService rentService;


    @PostMapping("/for-rent")
    public ResponseEntity<RentResponse> addRent(@Valid @RequestBody RentRequest RentRequest) {
        RentResponse saved = rentService.addRent(RentRequest);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}