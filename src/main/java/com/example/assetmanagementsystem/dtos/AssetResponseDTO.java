package com.example.assetmanagementsystem.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@Getter
@Setter
public class AssetResponseDTO {
    private Long id;
    private String status;
    private String name; // Assuming you want the asset name
    private String description; // Assuming you want the asset description
    private Long userId;
    private String userName;

}
