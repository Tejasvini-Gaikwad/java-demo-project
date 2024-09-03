package com.example.assetmanagementsystem.services;
import com.example.assetmanagementsystem.dtos.LoginUserDto;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.repositories.UserRepository;
//import com.tericcabrel.authapi.dtos.LoginUserDto;
//import com.tericcabrel.authapi.dtos.RegisterUserDto;
//import com.tericcabrel.authapi.entities.User;
//import com.tericcabrel.authapi.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
