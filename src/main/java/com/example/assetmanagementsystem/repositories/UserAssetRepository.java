package com.example.assetmanagementsystem.repositories;

import com.example.assetmanagementsystem.entities.UserAssets;
import com.example.assetmanagementsystem.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAssetRepository extends JpaRepository<UserAssets, Long> {
//    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.userAssets")
//    List<Users> findAllWithUserAssets();
boolean existsByUserIdAndAssetId(Long userId, Long assetId);
boolean existsByAssetId(Long assetId);
List<UserAssets> findByUserId(Long userId);

@Query("SELECT ua FROM UserAssets ua JOIN FETCH ua.asset WHERE ua.user.id = :userId")
List<UserAssets>findAllByUserId(Long userId);
}
