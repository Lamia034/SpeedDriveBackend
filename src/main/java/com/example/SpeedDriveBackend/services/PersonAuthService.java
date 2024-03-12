package com.example.SpeedDriveBackend.services;


import com.example.SpeedDriveBackend.dtos.request.LoginRequest;
import com.example.SpeedDriveBackend.dtos.request.RegisterRequest;
import com.example.SpeedDriveBackend.dtos.response.AuthResponse;

public interface PersonAuthService {

    AuthResponse login(LoginRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);

}
