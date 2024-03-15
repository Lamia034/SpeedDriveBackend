package com.example.SpeedDriveBackend.services.impl;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.CarSellRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.CarSellResponse;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import com.example.SpeedDriveBackend.exceptions.ResourceNotFoundException;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.services.CarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;

import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

//    @Override
//    @Transactional
//    public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
//        try {
//            CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
//
//            MultipartFile imageFile = carRentRequest.getImageFile();
//            if (imageFile != null && !imageFile.isEmpty()) {
//                String imagePath = saveImageToServer(imageFile);
//                carForRent.setImagePath(imagePath);
//            }
//
//            CarForRent savedCar = carForRentRepository.save(carForRent);
//
//            return modelMapper.map(savedCar, CarRentResponse.class);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save car: " + e.getMessage());
//        }
//    }

//    private String saveImageToServer(MultipartFile imageFile) throws IOException {
//        String fileName = UUID.randomUUID().toString(); // Generate a unique file name
//        String fileExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
//        String filePath = "../../../../resources/images/" + fileName + "." + fileExtension;
//
//        // Save the image file to the server's file system
//        imageFile.transferTo(new File(filePath));
//
//        return filePath;
//    }
//@Override
//@Transactional
//public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
//    try {
//        CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
//
//        MultipartFile imageFile = carRentRequest.getImageFile();
//        if (imageFile != null) {
//            byte[] imageBytes = imageFile.getBytes();
//            carForRent.setImage(imageBytes);
//        }
//
//        CarForRent savedCar = carForRentRepository.save(carForRent);
//
//        return modelMapper.map(savedCar, CarRentResponse.class);
//    } catch (Exception e) {
//        throw new RuntimeException("Failed to save car: " + e.getMessage());
//    }
//}


//    @Override
//    public CarRentResponse addCarForRent(CarRentRequest carRentRequest) {
//        try {
//            byte[] imageBytes = carRentRequest.getImage().getBytes(); // Convert MultipartFile to byte array
//
//            CarForRent carForRent = modelMapper.map(carRentRequest, CarForRent.class);
//            carForRent.setImage(imageBytes); // Set the image byte array to the CarForRent object
//
//            CarForRent savedCar = carForRentRepository.save(carForRent);
//
//            return modelMapper.map(savedCar, CarRentResponse.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to process image file: " + e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save car: " + e.getMessage());
//        }
//    }
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
        existingCar.setImagePath(carRentRequest.getImagePath());


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

    @Override
    @Transactional
    public CarRentResponse getCarForRentById(Long carRentId) {
        try {
            Optional<CarForRent> carOptional = carForRentRepository.findById(carRentId);
            if (carOptional.isPresent()) {
                CarForRent car = carOptional.get();
                CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
                responseDto.setEmail(car.getAgency().getEmail());
                responseDto.setName(car.getAgency().getName());
                return responseDto;
            } else {
                throw new ResourceNotFoundException("Car not found with ID: " + carRentId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve car: " + e.getMessage());
        }
    }

//@Override
//@Transactional
//public List<CarRentResponse> getAllCarsForRent(Pageable pageable) {
//    try {
//        Page<CarForRent> carPage = carForRentRepository.findAll(pageable);
//        List<CarForRent> cars = carPage.getContent();
//        List<CarRentResponse> responseDtoList = new ArrayList<>();
//
//        for (CarForRent car : cars) {
//            CarRentResponse responseDto = modelMapper.map(car, CarRentResponse.class);
//
//            // Assuming that CarForRent has a reference to Agency
//            responseDto.setName(car.getAgency().getName());
//            responseDto.setEmail(car.getAgency().getEmail());
//
//            responseDtoList.add(responseDto);
//        }
//
//        return responseDtoList;
//    } catch (Exception e) {
//        throw new RuntimeException("Failed to retrieve cars for rent by agency: " + e.getMessage());
//    }
//}
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



//public List<CarRentResponse> getAllCarsForRent(Pageable pageable) {
//    try {
//        Page<CarForRent> carPage = carForRentRepository.findAll(pageable);
//        return carPage.stream()
//                .map(this::mapCarForRentToResponse)
//                .collect(Collectors.toList());
//    } catch (Exception e) {
//        throw new RuntimeException("Failed to retrieve cars for rent: " + e.getMessage());
//    }
//}
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
}








