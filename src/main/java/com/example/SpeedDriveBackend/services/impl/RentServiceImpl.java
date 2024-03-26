package com.example.SpeedDriveBackend.services.impl;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.Client;
import com.example.SpeedDriveBackend.entities.Rent;
import com.example.SpeedDriveBackend.entities.abstracts.Car;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.example.SpeedDriveBackend.exceptions.ResourceNotFoundException;
import com.example.SpeedDriveBackend.exceptions.ResourceUnprocessableException;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.ClientRepository;
import com.example.SpeedDriveBackend.repositories.RentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.services.CarService;
import com.example.SpeedDriveBackend.services.RentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

        private final RentRepository rentRepository;
    private final CarForRentRepository carForRentRepository;
    private final ClientRepository clientRepository;

        private final ModelMapper modelMapper;


@Override
@Transactional
public RentResponse addRent(RentRequest rentRequest) {
    try {
        RentId rentId = new RentId();
        rentId.setClientId(rentRequest.getClientId());
        rentId.setCarRentId(rentRequest.getCarRentId());

        Client client = clientRepository.findById(rentRequest.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found."));

        CarForRent carForRent = carForRentRepository.findById(rentRequest.getCarRentId())
                .orElseThrow(() -> new ResourceNotFoundException("Car for rent not found."));

        Rent rent = new Rent();
        rent.setId(rentId);
        rent.setAgencyId(rentRequest.getAgencyId());
        rent.setStartDate(rentRequest.getStartDate());
        rent.setEndDate(rentRequest.getEndDate());
        rent.setClient(client);
        rent.setCarForRent(carForRent);

        carForRent.setCarStatus(CarStatus.RENTED);


        Rent savedRent = rentRepository.save(rent);

        return modelMapper.map(savedRent, RentResponse.class);
    } catch (Exception e) {
        throw new RuntimeException("Failed to save rent: " + e.getMessage());
    }
}


    @Override
    @Transactional
    public List<RentResponse> getRentsByAgency(UUID agencyId, Pageable pageable) {

            try {
                Page<Rent> rentPage = rentRepository.findByAgencyId(agencyId, pageable);
                List<Rent> rents = rentPage.getContent();
                List<RentResponse> responseDtoList = new ArrayList<>();

                for (Rent rent : rents) {
                    RentResponse responseDto = modelMapper.map(rent, RentResponse.class);


                    responseDtoList.add(responseDto);
                }

                return responseDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve rents for rent by agency: " + e.getMessage());
        }
    }
    @Override
    @Transactional
    public List<RentResponse> getRentsByClient(UUID clientId, Pageable pageable) {
        try {
            Page<Rent> rentPage = rentRepository.findByIdClientId(clientId, pageable);

            List<Rent> rents = rentPage.getContent();
            List<RentResponse> responseDtoList = new ArrayList<>();

            for (Rent rent : rents) {
                RentResponse responseDto = modelMapper.map(rent, RentResponse.class);

                responseDtoList.add(responseDto);
            }

            return responseDtoList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve rents for rent by client: " + e.getMessage());
        }
    }

//    @Override
//    @Transactional
//    public List<CarRentResponse> getRentsByClient(UUID agencyId, Pageable pageable) {
//        try {
//            Page<Rent> rentPage = rentRepository.findByClient_AgencyId(agencyId, pageable);
//            List<Rent> rents = rentPage.getContent();
//            List<RentResponse> responseDtoList = new ArrayList<>();
//
//            for (Rent rent : rents) {
//                // Get the latest rent for the rent
//                Optional<Rent> latestRent = rentRepository.findLatestRentByCarForRent(rent);
//
//
//                if (latestRent.isPresent()) {
//                    LocalDateTime startDate = latestRent.get().getStartDate();
//                    LocalDateTime endDate = latestRent.get().getEndDate();
//
//                    // Check if current time is between the start and end dates of the latest rent
//                    LocalDateTime currentTime = LocalDateTime.now();
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
@Override
@Transactional
public void deleteRentById(UUID clientId, Long carRentId) {
    try {
        // Check if the rent exists
        Optional<Rent> rentOptional = rentRepository.findByIdClientIdAndIdCarRentId(clientId, carRentId);

        if (rentOptional.isPresent()) {
            Rent rent = rentOptional.get();
            LocalDateTime startDate = rent.getStartDate();
            LocalDateTime endDate = rent.getEndDate();

            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(startDate) && now.isBefore(endDate)) {
                throw new ResourceUnprocessableException("You cannot delete this rent as the current time is within the rental period.");
            }
            // Rent found, delete it
            rentRepository.delete(rentOptional.get());
        } else {
            // Rent not found, throw an exception
            throw new EntityNotFoundException("Rent not found for clientId: " + clientId + " and carRentId: " + carRentId);
        }
    } catch (Exception e) {
        throw new RuntimeException("Failed to delete rent: " + e.getMessage(), e);
    }
}



}