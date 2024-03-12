package com.example.SpeedDriveBackend.services.impl;


import com.example.SpeedDriveBackend.dtos.request.LoginRequest;
import com.example.SpeedDriveBackend.dtos.request.RegisterRequest;
import com.example.SpeedDriveBackend.dtos.response.AuthResponse;
import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.entities.Client;
import com.example.SpeedDriveBackend.entities.abstracts.Person;
import com.example.SpeedDriveBackend.enumerations.Role;
import com.example.SpeedDriveBackend.repositories.AgencyRepository;
import com.example.SpeedDriveBackend.repositories.ClientRepository;
import com.example.SpeedDriveBackend.security.JwtService;
import com.example.SpeedDriveBackend.services.PersonAuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonAuthServiceImpl implements PersonAuthService {
private final ClientRepository clientRepository;
private final AgencyRepository agencyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Optional<Person> clientOptional = clientRepository.findUserByEmail(loginRequest.getEmail());
        Optional<Person> agencyOptional = agencyRepository.findUserByEmail(loginRequest.getEmail());

        if (clientOptional.isPresent()) {
            Person client = clientOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
                String jwtToken = jwtService.generateToken(client);
                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken(jwtToken);
                return authResponse;
            }
        } else if (agencyOptional.isPresent()) {
            Person agency = agencyOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), agency.getPassword())) {
                String jwtToken = jwtService.generateToken(agency);
                AuthResponse authResponse = new AuthResponse();
                authResponse.setToken(jwtToken);
                return authResponse;
            }
        }

        throw new UsernameNotFoundException("Email or password incorrect");
    }


    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        Person person;

        if (registerRequest.getRole() == Role.CLIENT) {
            Client client = modelMapper.map(registerRequest, Client.class);
            client.setClientId(UUID.randomUUID());  // Set UUID here
            person = client;
        } else if (registerRequest.getRole() == Role.AGENCY) {
            Agency agency = modelMapper.map(registerRequest, Agency.class);
            agency.setAgencyId(UUID.randomUUID());  // Set UUID here
            person = agency;
        } else {
            throw new IllegalArgumentException("Invalid role: " + registerRequest.getRole());
        }

        person.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        if (person instanceof Client) {
            clientRepository.save((Client) person);
        } else {
            agencyRepository.save((Agency) person);
        }

        String jwtToken = jwtService.generateToken(person);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        return authResponse;
    }

}
