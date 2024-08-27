package com.example.assetmanagementsystem.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetPostRequest {
    private String name;
    private String description;
    private Integer created_by;
    private Integer updated_by;
}
