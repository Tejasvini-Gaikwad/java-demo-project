package com.example.assetmanagementsystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
public class AssetManagementConfig {
    @Value("${regex.password.regexp}")
    private String regExPassword;
    @Value("${regex.phone.regexp}")
    private String regExPhone;
    @Value("${regex.email.regexp}")
    private String regExEmail;
}
