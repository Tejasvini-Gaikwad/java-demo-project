package com.example.assetmanagementsystem.controller;

import com.example.assetmanagementsystem.dtos.AssetsDto;
import com.example.assetmanagementsystem.dtos.UserAssetsResponseDTO;
import com.example.assetmanagementsystem.dtos.AssetPostRequest;
import com.example.assetmanagementsystem.entities.Assets;
import com.example.assetmanagementsystem.response.RequestResponse;
import com.example.assetmanagementsystem.services.AssetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AssetsController {
    private final AssetsService assetsService;

    @GetMapping("/assets")
    ResponseEntity<Iterable<AssetsDto>> getAssets(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        return assetsService.fetchAssets(keyword, page, size);
    }

    @PostMapping("/assets")
    public ResponseEntity<RequestResponse> saveAssets(@RequestBody AssetPostRequest asset, @RequestHeader("Authorization") String token) {
        return assetsService.processAssetSaving(asset, token);
    }

    @PutMapping("/assets/{id}")
    public ResponseEntity<RequestResponse> updateAsset(@PathVariable Long id, @RequestBody Assets asset, @RequestHeader("Authorization") String token) {
        return assetsService.processAssetUpdate(id, asset, token);
    }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<RequestResponse> deleteAsset(@PathVariable Long id) {
        return assetsService.processAssetDeletion(id);
    }

}
