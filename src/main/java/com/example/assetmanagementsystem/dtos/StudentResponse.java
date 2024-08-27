package com.example.assetmanagementsystem.dtos;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private UUID id;
    private String name;
    private String surname;
    private Date created;
    private Date updated;
}
