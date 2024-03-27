package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Car;
import com.example.SpeedDriveBackend.enumerations.CarStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static com.example.SpeedDriveBackend.enumerations.CarStatus.AVAILABLE;


//@Inheritance

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"rent"})
public class CarForRent extends Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carRentId;
    private double rentalPrice;
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus = AVAILABLE;
private UUID agencyId;
    @ManyToOne(fetch = FetchType.EAGER )
    @MapsId("agencyId")
    @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
    private Agency agency;
    @Override
    public String toString() {
        return "CarForRent [carRentId=" + carRentId + ", rentalPrice=" + rentalPrice + ", carStatus=" + carStatus + "]";
    }

}
