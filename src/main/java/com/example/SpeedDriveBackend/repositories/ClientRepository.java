package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.entities.Client;
import com.example.SpeedDriveBackend.entities.abstracts.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Person> findUserByEmail(String email);

}
