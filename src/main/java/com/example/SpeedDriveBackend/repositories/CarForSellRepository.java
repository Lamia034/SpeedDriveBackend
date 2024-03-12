package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.CarForSell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarForSellRepository extends JpaRepository<CarForSell, Long> {
}
