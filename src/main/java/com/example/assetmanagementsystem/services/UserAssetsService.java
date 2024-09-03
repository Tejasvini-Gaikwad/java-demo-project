package com.example.assetmanagementsystem.services;

import com.example.assetmanagementsystem.dtos.*;
import com.example.assetmanagementsystem.entities.Assets;
import com.example.assetmanagementsystem.entities.UserAssets;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.repositories.AssetRepository;
import com.example.assetmanagementsystem.repositories.UserAssetRepository;
import com.example.assetmanagementsystem.repositories.UserRepository;
import com.example.assetmanagementsystem.response.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserAssetsService {

    @Autowired
    private final UserAssetRepository userAssetsRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    @Autowired
    private UsersService usersService;

    @Autowired
    public UserAssetsService(UserRepository userRepository, AssetRepository assetRepository, UserAssetRepository userAssetsRepository) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.userAssetsRepository = userAssetsRepository;
    }

    public List<UserAssets> getAllUserAssets() {
        return userAssetsRepository.findAll();
    }

    public UserAssets saveUserAsset(UserAssets userAsset) {
        return userAssetsRepository.save(userAsset);
    }

    public UserAssets assignAssetToUser(Long userId, Long assetId) {
        Users user = userRepository.findById(userId).orElse(null);
        Assets asset = assetRepository.findById(assetId).orElse(null);

        if (user != null && asset != null) {
            UserAssets userAssets = new UserAssets(user, asset, "PENDING");
            return userAssetsRepository.save(userAssets);
        }
        return null; // Or throw an exception if preferred
    }

    public ResponseEntity<RequestResponse> handleAssetRequest(Long userId, Long assetId, String status) {
        if (isAssetAlreadyAssigned(userId, assetId)) {
            RequestResponse response = new RequestResponse("Asset already requested", HttpStatus.CONFLICT.value());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        UserAssets assetRequest = createAssetRequest(userId, assetId, status);
        if (assetRequest != null) {
            RequestResponse response = new RequestResponse(
                    "Asset requested successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            RequestResponse response = new RequestResponse("Asset not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    public UserAssets createAssetRequest(Long userId, Long assetId, String status) {
        Users user = userRepository.findById(userId).orElse(null);
        Assets asset = assetRepository.findById(assetId).orElse(null);

        if (user != null && asset != null) {
            UserAssets assetRequest = new UserAssets(user, asset, status);
            return userAssetsRepository.save(assetRequest);
        }
        return null; // Or throw an exception if preferred
    }

    public ResponseEntity<RequestResponse> processRequestStatusUpdate(Long requestId, String status) {
        boolean isUpdated = updateRequestStatus(requestId, status);

        if (isUpdated) {
            return new ResponseEntity<>(
                    new RequestResponse("Request status updated successfully", HttpStatus.OK.value()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new RequestResponse("Request not found or update failed", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    public boolean updateRequestStatus(Long requestId, String status) {
        UserAssets assetRequest = userAssetsRepository.findById(requestId).orElse(null);
        if (assetRequest != null) {
            assetRequest.setStatus(status);
            userAssetsRepository.save(assetRequest);
            return true;
        }else{
            return false;
        }
    }

    public boolean isAssetAlreadyAssigned(Long userId, Long assetId) {
        return userAssetsRepository.existsByUserIdAndAssetId(userId, assetId);
    }

    public AssetResponseDTO mapToDTO(UserAssets userAssets) {
        AssetResponseDTO dto = new AssetResponseDTO();
        dto.setId(userAssets.getId());
        dto.setStatus(userAssets.getStatus()); // Assuming Status is an Enum
        dto.setName(userAssets.getAsset().getName());
        dto.setDescription(userAssets.getAsset().getDescription());
        dto.setUserId(userAssets.getUser().getId());
        dto.setUserName(userAssets.getUser().getUsername());

        return dto;
    }


    public ResponseEntity<AssetResponseDTO> processGetRequestById(Long requestId) {
        UserAssets request = getRequestById(requestId);

        if (request != null) {
            AssetResponseDTO responseDTO = mapToDTO(request);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RequestResponse> assignAssetToEmployee(Long userId, AssignAssetRequest requestData) {
        Assets request = getAssetById(requestData.getAssetId());
        return this.handleAssetRequest(userId, requestData.getAssetId(), "ACCEPTED");
    }

    public Assets getAssetById(Long assetId) {
        return assetRepository.findById(assetId).orElse(null);
    }

    public UserAssets getRequestById(Long requestId) {
        return userAssetsRepository.findById(requestId).orElse(null);
    }

    private UserWithAssetsResponse convertToUserWithAssetsResponse(Users user) {
        Users userData = usersService.findByUserId(user.getCreated_by());
        List<UserAssetsDTO> userAssetsDTOs = user.getUserAssets().stream()
                .map(userAsset -> new UserAssetsDTO(
                        userAsset.getId(),
                        userAsset.getStatus(),
                        userAsset.getAsset().getName(),
                        userAsset.getAsset().getDescription()
                ))
                .collect(Collectors.toList());

        return new UserWithAssetsResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getUserType(),
                user.getEmail(),
                user.getPhone(),
                user.getFname(),
                user.getLname(),
                user.getStatus(),
                userData.getUsername(),
                user.getCreated_at(),
                userData.getUsername(),
                user.getUpdated_at(),
                userAssetsDTOs
        );
    }

    public ResponseEntity<List<UserAssetsResponseDTO>> getAllocatedAssets(Long userId) {
        try {
            List<UserAssets> userAssetsList = userAssetsRepository.findAllByUserId(userId);

            Map<Long, UserAssetsResponseDTO> userAssetsMap = new HashMap<>();
            for (UserAssets userAsset : userAssetsList) {
                UserAssetsResponseDTO responseDTO = userAssetsMap.get(userAsset.getId());

                if (responseDTO == null) {
                    responseDTO = new UserAssetsResponseDTO();
                    responseDTO.setId(userAsset.getId());
                    responseDTO.setStatus(userAsset.getStatus());
                    responseDTO.setAssets(new ArrayList<>()); // Initialize the list of assets
                    userAssetsMap.put(userAsset.getId(), responseDTO);
                }

                // Create an AssetsDto object (or similar) to store asset information
                AssetsDto assetDto = new AssetsDto();
                Users userData = usersService.findByUserId(userAsset.getAsset().getCreated_by());
                assetDto.setId(userAsset.getAsset().getId());
                assetDto.setName(userAsset.getAsset().getName());
                assetDto.setDescription(userAsset.getAsset().getDescription());
                assetDto.setCreated_at(userAsset.getAsset().getCreated_at());
                assetDto.setUpdated_at(userAsset.getAsset().getUpdated_at());
                assetDto.setCreated_by(userData.getUsername());
                assetDto.setUpdated_by(userData.getUsername());
                responseDTO.getAssets().add(assetDto);
            }
            List<UserAssetsResponseDTO> userAssetsResponseDTOList = new ArrayList<>(userAssetsMap.values());

            return new ResponseEntity<>(userAssetsResponseDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

