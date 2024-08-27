package com.example.assetmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    @Getter
    private String token;
    private long expiresIn;
    private long expirationTime;

}
