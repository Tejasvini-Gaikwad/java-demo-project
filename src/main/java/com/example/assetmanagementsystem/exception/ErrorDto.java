package com.example.assetmanagementsystem.exception;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ErrorDto {
    public ErrorDto() {

    }
    private String errorCode;
    private String errorMessage;
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }




}
