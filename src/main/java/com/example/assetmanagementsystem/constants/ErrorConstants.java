package com.example.assetmanagementsystem.constants;

import org.springframework.http.HttpStatus;

public class ErrorConstants {
    ErrorConstants() {
        throw new IllegalStateException("Error Constants Utility Class");
    }
    public static final String FAILED = "Failed to {0}";
    //Login Exception
    public static final String LOGIN_USER_NOT_FOUND_ERROR_CODE = "701";
    public static final String LOGIN_USER_NOT_FOUND_ERROR_MESSAGE = "Please provide the correct username or password.";

    public static final String NOT_FOUND_ERROR_CODE = "404";
    public static final String NOT_VALID_ERROR_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String NOT_VALID_ERROR_MESSAGE = "{0} is invalid.";
    public static final String NOT_VALID_ERROR_MESSAGE_DESC = "{0} is invalid. {1}";
    public static final String NOT_VALID_ERROR_MESSAGE_PASSWORD = "Password is not valid, It should have at least One Capital/Number/Special character and length is 8-15.";
    public static final String NOT_VALID_ERROR_MESSAGE_CHANGE_PASSWORD = "Password is not valid. It should not be same as previous one.";
    public static final String NOT_MATCH_ERROR_CODE = "723";
    public static final String NOT_MATCH_ERROR_MESSAGE = "{0} and {1} is not matching.";
    public static final String MANDATORY_ERROR_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    public static final String ALREADY_PRESENT_ERROR_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    public static final String SYSTEM_ERROR_CODE = "500";
    public static final String ALREADY_LOGGED_IN_ERROR_CODE = "601";
    public static final String ALREADY_LOGGED_IN_ERROR_MESSAGE = "You are already logged in from another device," +
            " Do you still want to continue to login here?";
    public static final String JWT_TOKEN_EXPIRED_ERROR_CODE = "401";
    public static final String JWT_TOKEN_EXPIRED_ERROR_MESSAGE = "JWT Token is expired.";
    public static final String UNAUTHORIZED_USER = "Unauthorized user";
    public static final String NOT_VALID_ERROR_MESSAGE_USERNAME = "Username is not valid, Only alphabets, numbers, dashes and underscores are allowed (The length of username must be minimum 3 and maximum 50)";
    public static final String NOT_VALID_ERROR_MESSAGE_FIRSTNAME = "Firstname is not valid, it must be 1-50 characters and contain only alphabets";
    public static final String NOT_VALID_ERROR_MESSAGE_LASTNAME = "Lastname is not valid, it must be 1-50 characters and contain only alphabets and apostrophe should be accepted only if it is between two letters";
    public static final String NOT_VALID_ERROR_MESSAGE_PHONE = "Phone number is not valid, it must be 1-15 characters and contain only +, -, and numerical digits";
}
