package com.example.assetmanagementsystem.repositories;

import com.example.assetmanagementsystem.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.userAssets WHERE u.username LIKE %:keyword% OR u.fname LIKE %:keyword% OR u.lname LIKE %:keyword%")
    Page<Users> findAllWithUserAssets(Pageable pageable, @Param("keyword") String keyword);
//    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.userAssets")
//    List<Users> findAllWithUserAssets();
    boolean existsByUsername(String username);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);

//    Page<Users> findByUsernameContainingIgnoreCaseOrFnameContainingIgnoreCaseOrLnameContainingIgnoreCase(String username, String fname, Pageable pageable);
//    Page<Users> findAllWithUserAssets(Pageable pageable);
}