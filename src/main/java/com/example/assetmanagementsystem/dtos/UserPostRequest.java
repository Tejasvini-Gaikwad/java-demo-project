package com.example.assetmanagementsystem.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPostRequest {
    private String username;
    private String password;
    private String email;
    private String fname;
    private String lname;
    private String phone;
    private String user_type;
    private String created_by;
    private String updated_by;
}
