package com.example.SpeedDriveBackend.controllers;

import com.example.SpeedDriveBackend.dtos.request.CarRentRequest;
import com.example.SpeedDriveBackend.dtos.request.RentRequest;
import com.example.SpeedDriveBackend.dtos.response.CarRentResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.services.CarService;
import com.example.SpeedDriveBackend.services.RentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rent")
@RequiredArgsConstructor


public class RentController {


    private final RentService rentService;


    @PostMapping("/for-rent")
    @PreAuthorize("hasAnyAuthority('AGENCY','CLIENT')")
    public ResponseEntity<RentResponse> addRent(@Valid @RequestBody RentRequest RentRequest) {
        RentResponse saved = rentService.addRent(RentRequest);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @GetMapping("/{agencyId}")
    @PreAuthorize("hasAuthority('AGENCY')")
    public List<RentResponse> getRentsByAgency(@PathVariable UUID agencyId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rentService.getRentsByAgency(agencyId ,pageable);
    }

    @GetMapping("/by-client/{clientId}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public List<RentResponse> getRentsByClient(@PathVariable UUID clientId, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rentService.getRentsByClient(clientId ,pageable);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public ResponseEntity<String> deleteRent(@RequestParam("clientId") UUID clientId, @RequestParam("carRentId") Long carRentId) {
        rentService.deleteRentById(clientId, carRentId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rent deleted successfully");
    }


}