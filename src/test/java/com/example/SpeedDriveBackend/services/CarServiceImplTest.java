package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.example.SpeedDriveBackend.enumerations.fuelType;
import com.example.SpeedDriveBackend.repositories.CarForRentRepository;
import com.example.SpeedDriveBackend.repositories.CarForSellRepository;
import com.example.SpeedDriveBackend.repositories.RentRepository;
import com.example.SpeedDriveBackend.services.impl.CarServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {
    @Mock
    private CarForRentRepository carForRentRepository;

    @Mock
    private RentRepository rentRepository;

    @Mock
    private CarForSellRepository carForSellRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCarForRent() {
        CarRentRequest carRentRequest = new CarRentRequest();
        carRentRequest.setMake("make");

        CarForRent savedCar = new CarForRent();
        savedCar.setMake("make");
        savedCar.setCarStatus(CarStatus.AVAILABLE);

        CarRentResponse expectedResponse = new CarRentResponse();
        expectedResponse.setMake("make");
        expectedResponse.setCarStatus(CarStatus.AVAILABLE);

        when(modelMapper.map(carRentRequest, CarForRent.class)).thenReturn(savedCar);
        when(carForRentRepository.save(savedCar)).thenReturn(savedCar);
        when(modelMapper.map(savedCar, CarRentResponse.class)).thenReturn(expectedResponse);

        CarRentResponse response = carService.addCarForRent(carRentRequest);

        assertEquals(expectedResponse, response);
        assertEquals(CarStatus.AVAILABLE, response.getCarStatus());
    }


    @Test
    public void testGetAllCarsForRent() {
        Page<CarForRent> carPage = mock(Page.class);
        when(carForRentRepository.findAll(any(Pageable.class))).thenReturn(carPage);

        Agency agency = new Agency();
        agency.setName("agency");
        CarForRent carForRent = new CarForRent();
        carForRent.setAgency(agency);
        List<CarForRent> carList = Collections.singletonList(carForRent);

        when(carPage.getContent()).thenReturn(carList);

        when(modelMapper.map(any(CarForRent.class), eq(CarRentResponse.class)))
                .thenAnswer(invocation -> {
                    CarForRent car = invocation.getArgument(0);
                    CarRentResponse response = new CarRentResponse();
                    response.setName(car.getAgency().getName());
                    return response;
                });

        List<CarRentResponse> response = carService.getAllCarsForRent(Pageable.unpaged());

        assertEquals(1, response.size());
        assertEquals("agency", response.get(0).getName());
    }

    @Test
    public void testGetCarsForRentByAgency() {
        UUID agencyId = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();
        Page<CarForRent> carPage = mock(Page.class);
        when(carForRentRepository.findByAgency_AgencyId(agencyId, pageable)).thenReturn(carPage);

        Agency agency = new Agency();
        agency.setName("Test Agency");
        agency.setEmail("test@example.com");
        CarForRent carForRent = new CarForRent();
        carForRent.setAgency(agency);
        List<CarForRent> carList = Collections.singletonList(carForRent);

        when(carPage.getContent()).thenReturn(carList);

        when(modelMapper.map(any(CarForRent.class), eq(CarRentResponse.class)))
                .thenAnswer(invocation -> {
                    CarForRent car = invocation.getArgument(0);
                    CarRentResponse response = new CarRentResponse();
                    response.setName(car.getAgency().getName());
                    response.setEmail(car.getAgency().getEmail());
                    response.setMake(car.getMake());
                    return response;
                });

        List<CarRentResponse> response = carService.getCarsForRentByAgency(agencyId, pageable);

        assertEquals(1, response.size());
        assertEquals("Test Agency", response.get(0).getName());
        assertEquals("test@example.com", response.get(0).getEmail());
    }

    @Test
    public void testUpdateCarForRent() {
        long carRentId = 1L;
        CarRentRequest carRentRequest = new CarRentRequest();
        carRentRequest.setMake("Test Make");
        carRentRequest.setModel("Test Model");
        carRentRequest.setManifacturingYear(2022);
        carRentRequest.setFuel(fuelType.DIESEL);
        carRentRequest.setRentalPrice(Double.valueOf(100.00));
        carRentRequest.setImagePath("test_image.jpg");
        carRentRequest.setCarStatus(CarStatus.AVAILABLE);

        CarForRent existingCar = new CarForRent();
        when(carForRentRepository.findById(carRentId)).thenReturn(Optional.of(existingCar));
        when(carForRentRepository.save(existingCar)).thenReturn(existingCar);

        CarRentResponse response = carService.updateCarForRent(carRentId, carRentRequest);

        assertEquals("Test Make", existingCar.getMake());
        assertEquals("Test Model", existingCar.getModel());
        assertEquals(2022, existingCar.getManifacturingYear());
        assertEquals(100.00, existingCar.getRentalPrice(), 0.001);
        assertEquals(fuelType.DIESEL, existingCar.getFuel());
        assertEquals("test_image.jpg", existingCar.getImagePath());
        assertEquals(CarStatus.AVAILABLE, existingCar.getCarStatus());

        verify(modelMapper).map(existingCar, CarRentResponse.class);
    }

    @Test
    public void testDeleteCarForRentById() {
        Long carRentId = 1L;
        carService.deleteCarForRentById(carRentId);
        verify(carForRentRepository).deleteByCarRentId(carRentId);
    }

    @Test
    public void testGetCarForRentById() {
        Long carRentId = 1L;
        Optional<CarForRent> carOptional = Optional.of(new CarForRent());
        when(carForRentRepository.findById(carRentId)).thenReturn(carOptional);

        Agency agency = new Agency();
        agency.setName("Test Agency");
        agency.setEmail("test@example.com");
        CarForRent carForRent = new CarForRent();
        carForRent.setAgency(agency);
        carOptional.get().setAgency(agency);

        when(modelMapper.map(any(CarForRent.class), eq(CarRentResponse.class)))
                .thenAnswer(invocation -> {
                    CarForRent car = invocation.getArgument(0);
                    CarRentResponse response = new CarRentResponse();
                    response.setName(car.getAgency().getName());
                    response.setEmail(car.getAgency().getEmail());
                    return response;
                });

        CarRentResponse response = carService.getCarForRentById(carRentId);

        assertNotNull(response);
        assertEquals("Test Agency", response.getName());
        assertEquals("test@example.com", response.getEmail());
    }
}
