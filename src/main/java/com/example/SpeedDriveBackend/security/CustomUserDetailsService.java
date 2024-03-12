package com.example.SpeedDriveBackend.security;

import com.example.SpeedDriveBackend.repositories.AgencyRepository;
import com.example.SpeedDriveBackend.repositories.ClientRepository;


import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final AgencyRepository agencyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var client = clientRepository.findUserByEmail(username);
        var agency = agencyRepository.findUserByEmail(username);

        if (client.isPresent()) {
            return new User(
                    client.get().getEmail(),
                    client.get().getPassword(),
                    client.get().getAuthorities()
            );
        } else if (agency.isPresent()) {
            return new User(
                    agency.get().getEmail(),
                    agency.get().getPassword(),
                    agency.get().getAuthorities()
            );
        } else {
            throw new UsernameNotFoundException("Email not found: " + username);
        }
    }
}
