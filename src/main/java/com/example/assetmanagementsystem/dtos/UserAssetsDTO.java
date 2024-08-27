package com.example.assetmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAssetsDTO {
    private Long id;
    private String status;
    private String assetName;
    private String assetDescription;

    // Constructor
    public UserAssetsDTO(Long id, String status, String assetName, String assetDescription) {
        this.id = id;
        this.status = status;
        this.assetName = assetName;
        this.assetDescription = assetDescription;
    }

    // Getters and Setters
    // ...
}
