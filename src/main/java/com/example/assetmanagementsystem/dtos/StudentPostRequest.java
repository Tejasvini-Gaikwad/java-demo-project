package com.example.assetmanagementsystem.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Builder
public class StudentPostRequest {
    private String name;
    private String surname;
}
