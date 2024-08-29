package com.example.assetmanagementsystem.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAssetsResponse {

    private Long id;
    private String username;
    private String password;
    private String user_type;
    private String email;
    private String phone;
    private String fname;
    private String lname;
    private String status;
    private String created_by;
    private Date created_at;
    private String updated_by;
    private Date updated_at;
    private List<UserAssetsDTO> userAssets;
}

