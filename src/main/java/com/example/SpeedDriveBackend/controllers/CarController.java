package com.example.SpeedDriveBackend.controllers;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor

public class CarController {


        private final CarService carService;



    @PostMapping("/for-rent")
        public ResponseEntity<CarRentResponse> addCarForRent(@Valid @RequestBody CarRentRequest carRentRequest) {
            CarRentResponse savedCar = carService.addCarForRent(carRentRequest);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        }

//    @PostMapping("/for-rent")
//    public ResponseEntity<CarRentResponse> addCarForRent(@Valid @ModelAttribute CarRentRequest carRentRequest, @RequestParam("imageNew") MultipartFile imageFile) {
//        CarRentResponse savedCar = carService.addCarForRent(carRentRequest, imageFile);
//        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
//    }
//    @PostMapping("/for-rent")
//    public ResponseEntity<CarRentResponse> addCarForRent(@Valid CarRentRequest carRentRequest,
//                                                         @RequestParam("image") MultipartFile image) {
//        try {
//            carRentRequest.setImage(image);
//            CarRentResponse savedCar = carService.addCarForRent(carRentRequest);
//            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to add car for rent: " + e.getMessage());
//        }
//    }


//@PostMapping("/for-rent")
//public ResponseEntity<CarRentResponse> addCarForRent(@RequestParam("image") MultipartFile image,
//                                                     @Valid @RequestBody CarRentRequest carRentRequest) {
//    if (!image.isEmpty()) {
//        MultipartFile imageData = image;
//        carRentRequest.setImage(imageData);
//    }
//
//    CarRentResponse savedCar = carService.addCarForRent(carRentRequest);
//    return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
//}


        @GetMapping("/for-rent")

        public List<CarRentResponse> getAllCarsForRent(@RequestParam int page, @RequestParam int size) {
                Pageable pageable = PageRequest.of(page, size);
                return carService.getAllCarsForRent(pageable);
         }

    @GetMapping("/for-rent/{agencyId}")
//    @PreAuthorize("hasAnyRole('AGENCY')")
//    @PreAuthorize("hasRole('AGENCY')")
    public List<CarRentResponse> getCarsForRentByAgency(@PathVariable UUID agencyId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carService.getCarsForRentByAgency(agencyId ,pageable);
    }



    @PutMapping("/for-rent/{carRentId}")
    public ResponseEntity<CarRentResponse> updateCarForRent(
            @PathVariable Long carRentId,
            @RequestBody CarRentRequest carRentRequest) {

        CarRentResponse updatedCar = carService.updateCarForRent(carRentId, carRentRequest);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/for-rent/{carRentId}")
    public ResponseEntity<String> deleteCarForRent(@PathVariable Long carRentId) {
        carService.deleteCarForRentById(carRentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Car deleted successfully");
    }

    @GetMapping("/for-rent/id/{carRentId}")
    public ResponseEntity<CarRentResponse> getCarForRentById(@PathVariable Long carRentId) {
        CarRentResponse carRent = carService.getCarForRentById(carRentId);
        return ResponseEntity.ok(carRent);
    }
//        @GetMapping("/for-sell")
//        public ResponseEntity<List<CarForSell>> getAllCarsForSell() {
//            List<CarForSell> carsForSell = carService.getAllCarsForSell();
//            return new ResponseEntity<>(carsForSell, HttpStatus.OK);
//        }

    }
