package com.example.assetmanagementsystem.dtos;

import com.example.assetmanagementsystem.entities.Assets;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class UserAssetsResponseDTO {
    private Long id;
    private String status;
    private List<AssetsDto> assets;

    // Constructor, Getters, and Setters
}


