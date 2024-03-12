package com.example.SpeedDriveBackend.services.impl;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.services.CarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarForRentRepository carForRentRepository;

    private final CarForSellRepository carForSellRepository;
    private final ModelMapper modelMapper;


    @Override
    public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
        try {
            CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
            CarForRent savedCar = carForRentRepository.save(carForRent);

            return modelMapper.map(savedCar, CarRentResponse.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to save car: " + e.getMessage());
        }
    }
//@Override
//public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
//    try {
//        // Map the CarForRent entity from CarRentRequest
//        CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
//
//        // Save the CarForRent entity
//        CarForRent savedCar = carForRentRepository.save(carForRent);
//
//        // Map the CarRentResponse
//        CarRentResponse responseDto = modelMapper.map(savedCar, CarRentResponse.class);
//
//        // Set name and email properties manually from the associated Agency
//        responseDto.setName(savedCar.getAgency().getName());
//        responseDto.setEmail(savedCar.getAgency().getEmail());
//
//        return responseDto;
//    } catch (Exception e) {
//        throw new RuntimeException("Failed to save car: " + e.getMessage());
//    }
//}


    @Override
    public CarSellResponse addCarForSell(CarSellRequest carSellRequest) {
        try {
            CarForSell carForSell = modelMapper.map(carSellRequest, CarForSell.class);
            CarForSell savedCar = carForSellRepository.save(carForSell);
            return modelMapper.map(savedCar, CarSellResponse.class);

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
            List<CarRentResponse> responseDtoList = new ArrayList<>();

            for (CarForRent car : cars) {
                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
                responseDto.setName(car.getAgency().getName());
                responseDto.setEmail(car.getAgency().getEmail());

                responseDtoList.add(responseDto);
            }

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


        CarForRent savedCar = carForRentRepository.save(existingCar);

        return modelMapper.map(savedCar, CarRentResponse.class);
    }


    @Override
    @Transactional
    public boolean deleteCarForRentById(Long carRentId) {
        try {
            carForRentRepository.findById(carRentId).ifPresentOrElse(
                    carForRentRepository::delete,
                    () -> {
                        throw new EntityNotFoundException("Car for rent not found with ID: " + carRentId);
                    }
            );
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete car for rent: " + e.getMessage(), e);
        }
    }

}


//    public List<CarRentResponse> getAllCarsForRent(Pageable pageable) {
//        try {
//            Page<CarForRent> carPage = carForRentRepository.findAll(pageable);
//            List<CarRentResponse> responseList = new ArrayList<>();
//
//            for (CarForRent car : carPage) {
//                // Fetch rents if needed
//                car.getRents().size(); // This will initialize the collection
//                CarRentResponse response = modelMapper.map(car, CarRentResponse.class);
//                responseList.add(response);
//            }
//
//            return responseList;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve cars for rent: " + e.getMessage());
//        }
//    }

//@Override
//    public List<CarRentResponse> getAllCarsForRent(Pageable pageable) {
//        try {
//            Page<CarForRent> carPage = carForRentRepository.findAll(pageable);
//            return carPage.stream()
//                    .map(this::mapCarForRentToResponse)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to retrieve cars for rent: " + e.getMessage());
//        }
//    }
//
//    private CarRentResponse mapCarForRentToResponse(CarForRent carForRent) {
//        TypeMap<CarForRent, CarRentResponse> typeMap = modelMapper.getTypeMap(CarForRent.class, CarRentResponse.class);
//        if (typeMap == null) {
//            typeMap = modelMapper.createTypeMap(CarForRent.class, CarRentResponse.class);
//            // Add custom mappings if needed
//        }
//
//        // Add mapping for List<Rent> to List<RentResponse>
//        typeMap.addMappings(mapper -> mapper.map(src -> src.getRents(), CarRentResponse::setRents));
//
//        return typeMap.map(carForRent);
//    }

//    @Override
//    public List<CarForRent> getAllCarsForRent() {
//        return carForRentRepository.findAll();
//    }
//
//    @Override
//    public List<CarForSell> getAllCarsForSell() {
//        return carForSellRepository.findAll();
//    }


