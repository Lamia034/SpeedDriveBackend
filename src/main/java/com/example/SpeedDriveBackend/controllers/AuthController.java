package com.example.SpeedDriveBackend.controllers;

import com.example.SpeedDriveBackend.dtos.request.LoginRequest;
import com.example.SpeedDriveBackend.dtos.request.RegisterRequest;
import com.example.SpeedDriveBackend.dtos.response.AuthResponse;
import com.example.SpeedDriveBackend.services.PersonAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final PersonAuthService personAuthService;

    @RequestMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(personAuthService.login(loginRequest), HttpStatus.OK);
    }

    @RequestMapping("/register")
    public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(personAuthService.register(registerRequest), HttpStatus.OK);
    }

}
