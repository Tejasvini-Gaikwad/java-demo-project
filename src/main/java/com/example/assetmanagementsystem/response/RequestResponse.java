package com.example.assetmanagementsystem.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestResponse {
    // Getter and setter
    private String message;
    private int status;

    public RequestResponse(String message, int statusCode) {
        this.message = message;
        this.status = statusCode;
    }

}



