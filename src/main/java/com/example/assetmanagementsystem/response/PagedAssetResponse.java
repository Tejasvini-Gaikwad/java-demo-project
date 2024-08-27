package com.example.assetmanagementsystem.response;

import com.example.assetmanagementsystem.dtos.UserAssetsResponseDTO;

import java.util.List;

public class PagedAssetResponse {

    private List<UserAssetsResponseDTO> assets;
    private long totalRecords;
    private int totalPages;
    private int currentPage;

    // Constructors, getters, and setters

    public PagedAssetResponse(List<UserAssetsResponseDTO> assets, long totalRecords, int totalPages, int currentPage) {
        this.assets = assets;
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<UserAssetsResponseDTO> getAssets() {
        return assets;
    }

    public void setAssets(List<UserAssetsResponseDTO> assets) {
        this.assets = assets;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

