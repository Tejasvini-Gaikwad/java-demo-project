package com.example.assetmanagementsystem.controller;

import com.example.assetmanagementsystem.dtos.AssetResponseDTO;
import com.example.assetmanagementsystem.dtos.AssignAssetRequest;
import com.example.assetmanagementsystem.dtos.UserAssetsResponseDTO;
import com.example.assetmanagementsystem.dtos.UserPostRequest;
import com.example.assetmanagementsystem.dtos.UserWithAssetsResponse;
import com.example.assetmanagementsystem.entities.Users;
import com.example.assetmanagementsystem.repositories.UserRepository;
import com.example.assetmanagementsystem.response.RequestResponse;
import com.example.assetmanagementsystem.entities.UserAssets;
import com.example.assetmanagementsystem.repositories.UserAssetRepository;
import com.example.assetmanagementsystem.security.CustomSecurityExpressions;
import com.example.assetmanagementsystem.services.UserAssetsService;
import com.example.assetmanagementsystem.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UsersController {

    private final UserAssetRepository userAssetRepository;
    private final UsersService usersService;
    private final UserAssetsService userAssetsService;
    private final UserRepository userRepository;
    private final CustomSecurityExpressions customSecurityExpressions;

    @PreAuthorize("@customSecurityExpressions.isAdmin()")
    @GetMapping("/user")
    ResponseEntity<List<UserWithAssetsResponse>> getUsers(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        return usersService.getAllUsersResponse(keyword, page, size);
    }

    @PreAuthorize("@customSecurityExpressions.isAdmin()")
    @PostMapping("/user")
    public ResponseEntity<RequestResponse> saveUser(@RequestBody UserPostRequest userPostRequest, @RequestHeader("Authorization") String token) {
        return usersService.processAndSaveUser(userPostRequest, token);
    }

    @PreAuthorize("@customSecurityExpressions.isAdmin()")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable Long id) {
        return usersService.deleteUserResponse(id);
    }

    @PreAuthorize("@customSecurityExpressions.isEmployee()")
    @PostMapping("/request-asset")
    public ResponseEntity<RequestResponse> requestAsset(
            @RequestParam Long userId, @RequestParam Long assetId ) {
        return userAssetsService.handleAssetRequest(userId, assetId, "PENDING");
    }

    @PreAuthorize("@customSecurityExpressions.isAdmin()")
    @PatchMapping("/accept-reject-request/{requestId}")
    public ResponseEntity<RequestResponse> updateRequestStatus(
            @PathVariable Long requestId, @RequestParam String status) {
        return userAssetsService.processRequestStatusUpdate(requestId, status);
    }

    @GetMapping("/get-asset-request/{requestId}")
    public ResponseEntity<AssetResponseDTO> getRequestById(@PathVariable Long requestId) {
        return userAssetsService.processGetRequestById(requestId);
    }

    @PreAuthorize("@customSecurityExpressions.isAdmin()")
    @PostMapping("/assign-asset/{userId}")
    public ResponseEntity<RequestResponse> assignAsset(@PathVariable Long userId, @RequestBody AssignAssetRequest requestData) {
        return userAssetsService.assignAssetToEmployee(userId, requestData);
    }

    @PatchMapping("/user/update-profile/{id}")
    public ResponseEntity<RequestResponse> updateProfile(@PathVariable Long id, @RequestBody UserPostRequest userPostRequest) {
        return usersService.processUpdateProfile(id, userPostRequest);
    }

    @GetMapping("/check-allocated-assets/{userId}")
    ResponseEntity<List<UserAssetsResponseDTO>> checkAllocatedAssets(@PathVariable Long userId) {
        return userAssetsService.getAllocatedAssets(userId);
    }

}
