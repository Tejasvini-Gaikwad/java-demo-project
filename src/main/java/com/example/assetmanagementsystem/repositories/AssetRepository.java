package com.example.assetmanagementsystem.repositories;

import com.example.assetmanagementsystem.entities.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface AssetRepository extends JpaRepository<Assets, Long> {
    boolean existsByName(String name);
    Page<Assets> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

}
