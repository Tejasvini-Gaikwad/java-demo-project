package com.example.assetmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserAssetsResponseDTO {
    private Long id;
    private String status;
    private List<AssetsDto> assets;  // Correctly define this as a list

    // Constructor, Getters, and Setters
}
