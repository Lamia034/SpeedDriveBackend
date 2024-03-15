package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, RentId> {
}
