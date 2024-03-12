package com.example.SpeedDriveBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.SpeedDriveBackend.entities")
public class SpeedDriveBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeedDriveBackendApplication.class, args);
	}

}
