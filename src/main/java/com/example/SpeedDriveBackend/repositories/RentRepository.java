package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.embeddable.RentId;
import com.example.SpeedDriveBackend.entities.CarForRent;
import com.example.SpeedDriveBackend.entities.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentRepository extends JpaRepository<Rent, RentId> {
//    Optional<Rent> findLatestRentByCarForRent(CarForRent car);

//    Page<Rent> findByAgency_AgencyId(UUID agencyId, Pageable pageable);

    @Query("SELECT c FROM Rent c WHERE c.agencyId = :agencyId")
    Page<Rent> findByAgencyId(@Param("agencyId") UUID agencyId,Pageable pageable);

    Page<Rent> findByIdClientId(UUID clientId, Pageable pageable);

    Optional<Rent> findById(RentId rentId);

    Optional<Rent> findByIdClientIdAndIdCarRentId(UUID clientId, Long carRentId);


//    Page<Rent> findByAgencyId(UUID agencyId, Pageable pageable);

//    Page<Rent> findByAgency_AgencyId(UUID agencyId, Pageable pageable);

//    List<Rent> findOverlappingRentals(Long carId, LocalDateTime startDate, LocalDateTime endDate);
}
