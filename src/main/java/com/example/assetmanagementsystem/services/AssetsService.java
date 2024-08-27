package com.example.assetmanagementsystem.services;

import com.example.assetmanagementsystem.dtos.AssetsDto;
import com.example.assetmanagementsystem.dtos.UserAssetsResponseDTO;
import com.example.assetmanagementsystem.dtos.AssetPostRequest;
import com.example.assetmanagementsystem.entities.Assets;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.repositories.AssetRepository;
import com.example.assetmanagementsystem.repositories.UserAssetRepository;
import com.example.assetmanagementsystem.response.RequestResponse;
import com.example.assetmanagementsystem.validator.AssetValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AssetsService {
    private final AssetRepository assetRepository;
    private final UserAssetRepository userAssetRepository;
    private final AssetValidator assetValidator;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public ResponseEntity<Iterable<AssetsDto>> fetchAssets(String keyword, int page, int size) {
        try {
            Iterable<AssetsDto> assets = getAllAssets(keyword, page, size);
            return new ResponseEntity<>(assets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Iterable<AssetsDto> getAllAssets(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Assets> assets;
        if (keyword != null && !keyword.isEmpty()) {
            assets = assetRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
        } else {
            assets = assetRepository.findAll(pageable);
        }
        return assets.stream().map(asset -> {
            AssetsDto dto = new AssetsDto();
            dto.setId(asset.getId());
            dto.setName(asset.getName());
            dto.setDescription(asset.getDescription());
            dto.setCreated_by(Math.toIntExact(asset.getCreated_by()));
            dto.setCreated_at(asset.getCreated_at());
            dto.setUpdated_by(Math.toIntExact(asset.getUpdated_by()));
            dto.setUpdated_at(asset.getUpdated_at());
            return dto;
        }).collect(Collectors.toList());
    }

    public ResponseEntity<RequestResponse> processAssetSaving(AssetPostRequest asset, String token) {
        token = token.substring(7);
        UserDetails userDetails = jwtService.getUserDetailsFromToken(token, userDetailsService);
        Long userId = null;
        if (userDetails instanceof Users) {
            userId = ((Users) userDetails).getId();
        } else {
            System.out.println("The userDetails object is not an instance of Users.");
        }
        assetValidator.validateAssetRequest(asset, "CREATE");
        Assets new_asset = new Assets();
        new_asset.setName(asset.getName());
        new_asset.setDescription(asset.getDescription());
        new_asset.setCreated_by(userId);
        new_asset.setUpdated_by(userId);
        new_asset.setCreated_at(new Date());
        new_asset.setUpdated_at(new Date());
        saveAsset(new_asset);
        RequestResponse response = new RequestResponse("Asset saved successfully.", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Assets saveAsset(Assets asset) {
        return assetRepository.save(asset);
    }

    public ResponseEntity<RequestResponse> processAssetUpdate(Long id, Assets asset, String token) {
        Assets updatedAsset = updateAsset(id, asset, token);
        if (updatedAsset != null) {
            RequestResponse response = new RequestResponse("Asset updated successfully.", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            RequestResponse response = new RequestResponse("Asset not found.", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public Assets updateAsset(Long id, Assets asset, String token) {
        if (assetRepository.existsById(id)) {
            Optional<Assets> assets = assetRepository.findById(id);
            Assets assetData = assets.get(); // Unwrap the Optional to get the Assets object
            Long assetId = assetData.getId();
            token = token.substring(7);
            UserDetails userDetails = jwtService.getUserDetailsFromToken(token, userDetailsService);
            Long userId = null;
            if (userDetails instanceof Users) {
                userId = ((Users) userDetails).getId();
            } else {
                System.out.println("The userDetails object is not an instance of Users.");
            }
            asset.setId(id); // Set the ID to ensure the existing entity is updated
            asset.setName(asset.getName());
            asset.setDescription(asset.getDescription());
            asset.setCreated_by(assetId);
            asset.setUpdated_by(userId);
            return assetRepository.save(asset);
        } else {
            return null; // Entity not found
        }
    }

    public boolean doesAssetExist(String name) {
        return assetRepository.existsByName(name);
    }

    public ResponseEntity<RequestResponse> processAssetDeletion(Long id) {
        Boolean isDeleted = deleteAsset(id);
        if (isDeleted == null) {
            return new ResponseEntity<>(
                    new RequestResponse("Please deallocate the asset from the user before deleting.", HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST
            );
        } else if (isDeleted) {
            return new ResponseEntity<>(
                    new RequestResponse("Asset deleted successfully", HttpStatus.OK.value()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new RequestResponse("Asset not found or deletion failed", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    public Boolean deleteAsset(Long id) {
        if (!assetRepository.existsById(id)) {
            return false; // Asset not found
        }
        // Check if the asset is allocated to any user
        if (userAssetRepository.existsByAssetId(id)) {
            return null;
        }
        // Delete the asset
        assetRepository.deleteById(id);
        return true;
    }
}
