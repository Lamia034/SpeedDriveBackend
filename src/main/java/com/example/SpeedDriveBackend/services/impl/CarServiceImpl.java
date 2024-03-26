package com.example.SpeedDriveBackend.services.impl;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.example.SpeedDriveBackend.exceptions.ResourceNotFoundException;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.repositories.RentRepository;
import com.example.SpeedDriveBackend.services.CarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarForRentRepository carForRentRepository;
    private final RentRepository rentRepository;

    private final CarForSellRepository carForSellRepository;
    private final ModelMapper modelMapper;


    @Override
    public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
        try {
            CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
            carForRent.setCarStatus(CarStatus.AVAILABLE);
            CarForRent savedCar = carForRentRepository.save(carForRent);

            return modelMapper.map(savedCar, CarRentResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save car: " + e.getMessage());
        }
    }





    @Override
    @Transactional
    public List<CarRentResponse> getAllCarsForRent(Pageable pageable) {
        try {
            Page<CarForRent> carPage = carForRentRepository.findAll(pageable);
            List<CarForRent> cars = carPage.getContent();
            System.out.println("=>"+cars);
            List<CarRentResponse> responseDtoList = new ArrayList<>();

            for (CarForRent car : cars) {
                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
                responseDto.setName(car.getAgency().getName());
                responseDto.setEmail(car.getAgency().getEmail());

                responseDtoList.add(responseDto);
            }
            System.out.println("=>"+cars);

            return responseDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve cars for rent: " + e.getMessage());
        }
    }





    @Override
    @Transactional
    public List<CarRentResponse> getCarsForRentByAgency(UUID agencyId, Pageable pageable) {
        try {
            Page<CarForRent> carPage = carForRentRepository.findByAgency_AgencyId(agencyId, pageable);
            List<CarForRent> cars = carPage.getContent();
            List<CarRentResponse> responseDtoList = new ArrayList<>();

            for (CarForRent car : cars) {
                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
                responseDto.setName(car.getAgency().getName());
                responseDto.setEmail(car.getAgency().getEmail());

                responseDtoList.add(responseDto);
            }

            return responseDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve cars for rent by agency: " + e.getMessage());
        }
    }




    @Override
    @Transactional
    public CarRentResponse updateCarForRent(long carRentId, CarRentRequest carRentRequest) {
        CarForRent existingCar = carForRentRepository.findById(carRentId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with ID: " + carRentId));

        existingCar.setMake(carRentRequest.getMake());
        existingCar.setModel(carRentRequest.getModel());
        existingCar.setManifacturingYear(carRentRequest.getManifacturingYear());
        existingCar.setFuel(carRentRequest.getFuel());
        existingCar.setRentalPrice(carRentRequest.getRentalPrice());
        existingCar.setImagePath(carRentRequest.getImagePath());
        existingCar.setCarStatus(carRentRequest.getCarStatus());

        CarForRent savedCar = carForRentRepository.save(existingCar);

        return modelMapper.map(savedCar, CarRentResponse.class);
    }


    @Override
    @Transactional
    public void deleteCarForRentById(Long carRentId) {
        carForRentRepository.deleteByCarRentId(carRentId);
    }




    @Override
    @Transactional


    public CarRentResponse getCarForRentById(Long carRentId) {
        try {
            Optional<CarForRent> carOptional = carForRentRepository.findById(carRentId);
            if (carOptional.isPresent()) {
                CarForRent car = carOptional.get();
                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
                responseDto.setName(car.getAgency().getName());
                responseDto.setEmail(car.getAgency().getEmail());
                return responseDto;
            } else {
                throw new ResourceNotFoundException("Car not found with ID: " + carRentId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve car: " + e.getMessage());
        }
    }




    //    public CarRentResponse getCarForRentById(Long carRentId) {
//        try {
//            Optional<CarForRent> carOptional = carForRentRepository.findById(carRentId);
//            if (carOptional.isPresent()) {
//                CarForRent car = carOptional.get();
//                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
//                responseDto.setEmail(car.getAgency().getEmail());
//                responseDto.setName(car.getAgency().getName());
//                return responseDto;
//            } else {
//                throw new ResourceNotFoundException("Car not found with ID: " + carRentId);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve car: " + e.getMessage());
//        }
//    }

//        try {
//            Optional<CarForRent> carOptional = carForRentRepository.findById(carRentId);
//            if (carOptional.isPresent()) {
//                CarForRent car = carOptional.get();
//                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
//
//                // Fetch associated Rent entities
//                List<Rent> rents = car.getRents();
//
//                // Map Rent entities to DTOs
//                List<Rent> rentResponses = rents.stream()
//                        .map(rent -> modelMapper.map(rent, Rent.class))
//                        .collect(Collectors.toList());
//
//                // Set the list of RentResponse DTOs in the CarRentResponse DTO
//                responseDto.setRents(rentResponses);
//
//                // Set other fields if needed
//                responseDto.setEmail(car.getAgency().getEmail());
//                responseDto.setName(car.getAgency().getName());
//
//                return responseDto;
//            } else {
//                throw new ResourceNotFoundException("Car not found with ID: " + carRentId);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve car: " + e.getMessage());
//        }


    //    @Override
//    @Transactional
//    public List<CarRentResponse> getCarsForRentByAgency(UUID agencyId, Pageable pageable) {
//        try {
//            Page<CarForRent> carPage = carForRentRepository.findByAgency_AgencyId(agencyId, pageable);
//            List<CarForRent> cars = carPage.getContent();
//            List<CarRentResponse> responseDtoList = new ArrayList<>();
//
//            for (CarForRent car : cars) {
//                Optional<Rent> latestRent = rentRepository.findLatestRentByCarForRent(car);
//
//                boolean isAvailable = true;
//
//                if (latestRent.isPresent()) {
//                    LocalDateTime startDate = latestRent.get().getStartDate();
//                    LocalDateTime endDate = latestRent.get().getEndDate();
//
//                    LocalDateTime currentTime = LocalDateTime.now();
//                    isAvailable = currentTime.isBefore(startDate) || currentTime.isAfter(endDate);
//                }
//
//                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
//                responseDto.setCarStatus(isAvailable);
//                responseDto.setName(car.getAgency().getName());
//                responseDto.setEmail(car.getAgency().getEmail());
//
//                responseDtoList.add(responseDto);
//            }
//
//            return responseDtoList;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve cars for rent by agency: " + e.getMessage());
//        }
//    }


//    public boolean isAvailable(Long carId, LocalDateTime startDate, LocalDateTime endDate) {
//        List<Rent> overlappingRentals = rentRepository.findOverlappingRentals(carId, startDate, endDate);
//        return overlappingRentals.isEmpty();
//    }

    //    @Override
//    @Transactional
//    public CarRentResponse getCarForRentById(Long carRentId) {
//        try {
//            Optional<CarForRent> carOptional = carForRentRepository.findById(carRentId);
//            if (carOptional.isPresent()) {
//                return modelMapper.map(carOptional.get(), CarRentResponse.class);
//            } else {
//                throw new ResourceNotFoundException("car not found with ID: " + carRentId);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve car: " + e.getMessage());
//        }
//    }
}








