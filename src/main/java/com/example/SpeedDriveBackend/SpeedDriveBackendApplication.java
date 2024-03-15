package com.example.SpeedDriveBackend;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@EntityScan("com.example.SpeedDriveBackend.entities")
//@EnableMultipart
//@MultipartConfig
// @Configuration

public class SpeedDriveBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeedDriveBackendApplication.class, args);
	}
	// Configure the maximum file size and request size
//	@Bean
//	public MultipartConfigElement multipartConfigElement() {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setMaxFileSize(DataSize.parse("10MB")); // Set the maximum file size
//		factory.setMaxRequestSize(DataSize.parse("10MB")); // Set the maximum request size
//		return factory.createMultipartConfig();
//	}
}
