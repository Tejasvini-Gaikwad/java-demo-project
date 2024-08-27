package com.example.assetmanagementsystem.controller;
import com.example.assetmanagementsystem.dtos.LoginResponse;
import com.example.assetmanagementsystem.dtos.LoginUserDto;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.services.AuthenticationService;
import com.example.assetmanagementsystem.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        Users authenticatedUser = authenticationService.authenticate(loginUserDto);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        Date tomorrowDate = java.util.Date.from(tomorrow.atStartOfDay(ZoneId.systemDefault()).toInstant());

        String jwtToken = jwtService.generateToken(authenticatedUser, Date.from(Instant.now().plus(Duration.ofHours(1))), "");
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime()); // Make sure getExpirationTime() returns long
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
