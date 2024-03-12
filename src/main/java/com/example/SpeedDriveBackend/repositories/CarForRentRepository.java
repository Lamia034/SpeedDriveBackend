package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.entities.CarForRent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarForRentRepository extends JpaRepository<CarForRent, Long> {
    Page<CarForRent> findByAgency_AgencyId(UUID agencyId, Pageable pageable);
}
