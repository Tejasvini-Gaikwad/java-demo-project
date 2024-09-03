package com.example.assetmanagementsystem.constants;
import org.springframework.http.HttpStatus;

public class ErrorConstants {
    ErrorConstants() {
        throw new IllegalStateException("Error Constants Utility Class");
    }
    public static final String NOT_VALID_ERROR_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String NOT_VALID_ERROR_MESSAGE_PASSWORD = "Password is not valid, It should have at least One Capital/Number/Special character and length is 8-15.";
    public static final String MANDATORY_ERROR_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String ALREADY_PRESENT_ERROR_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    public static final String NOT_VALID_ERROR_MESSAGE_USERNAME = "Username is not valid, Only alphabets, numbers, dashes and underscores are allowed (The length of username must be minimum 3 and maximum 50)";
    public static final String NOT_VALID_ERROR_MESSAGE_FIRSTNAME = "Firstname is not valid, it must be 1-50 characters and contain only alphabets";
    public static final String NOT_VALID_ERROR_MESSAGE_LASTNAME = "Lastname is not valid, it must be 1-50 characters and contain only alphabets and apostrophe should be accepted only if it is between two letters";
}
