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
import com.example.SpeedDriveBackend.exceptions.ResourceNotFoundException;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.ClientRepository;
import com.example.SpeedDriveBackend.repositories.RentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.services.CarService;
import com.example.SpeedDriveBackend.services.RentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

        private final RentRepository rentRepository;
    private final CarForRentRepository carForRentRepository;
    private final ClientRepository clientRepository;

        private final ModelMapper modelMapper;

//
//        @Override
//        @Transactional
//        public RentResponse addRent(RentRequest RentRequest) {
//            try {
//                Rent rent = modelMapper.map(RentRequest, Rent.class);
//                Rent saved = rentRepository.save(rent);
//
//                return modelMapper.map(saved, RentResponse.class);
//
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to save rent: " + e.getMessage());
//            }
//        }
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
        rent.setStartDate(rentRequest.getStartDate());
        rent.setEndDate(rentRequest.getEndDate());
        rent.setClient(client);
        rent.setCarForRent(carForRent);

        Rent savedRent = rentRepository.save(rent);

        return modelMapper.map(savedRent, RentResponse.class);
    } catch (Exception e) {
        throw new RuntimeException("Failed to save rent: " + e.getMessage());
    }
}


}