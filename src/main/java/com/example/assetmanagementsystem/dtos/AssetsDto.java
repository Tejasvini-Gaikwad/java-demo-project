package com.example.assetmanagementsystem.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AssetsDto {
    private Long id;
    private String name;
    private String description;
    private Integer created_by;
    private Date created_at;
    private Integer updated_by;
    private Date updated_at;
}
