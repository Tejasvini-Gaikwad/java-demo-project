package com.example.assetmanagementsystem.constants;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ErrorConstantString {
    private ErrorConstantString() {
        throw new IllegalStateException("Error Constant String Utility Class");
    }

    public static String notValidErrorMessage(String text) {
        final Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(AppConstants.PHONE, "Phone Number is not valid");
        errorMessage.put(AppConstants.EMAIL, "Email is not valid");
        errorMessage.put(AppConstants.PASSWORD, "Password is not valid");
        return errorMessage.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
    }

    public static String notValidErrorMessagePassword(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_PASSWORD;
    }

    public static String mandatoryErrorMessage(String text) {
        final Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(AppConstants.DESCRIPTION, "Description is mandatory");
        errorMessage.put(AppConstants.PHONE, "Phone number is mandatory");
        errorMessage.put(AppConstants.NAME, "Name is mandatory");
        errorMessage.put(AppConstants.USERNAME, "User Name is mandatory");
        errorMessage.put(AppConstants.PASSWORD, "Password is mandatory");
        errorMessage.put(AppConstants.FIRST_NAME, "First Name is mandatory");
        errorMessage.put(AppConstants.LAST_NAME, "Last Name is mandatory");
        errorMessage.put(AppConstants.EMAIL, "Email is mandatory");
        return errorMessage.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
    }

    public static String alreadyPresentErrorMessage(String text, String var2) {
        final Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put(AppConstants.NAME, "Name: {0} already exists");
        errorMessage.put(AppConstants.USERNAME, "User Name: {0} already exists");
        errorMessage.put(AppConstants.EMAIL, "Email: {0} already exists");
        String messageTemplate = errorMessage.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
        return MessageFormat.format(messageTemplate, var2);
    }

    public static String notValidErrorMessageUsername() {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_USERNAME;
    }

    public static String notValidErrorMessageFirstname() {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_FIRSTNAME;
    }

    public static String notValidErrorMessageLastname() {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_LASTNAME;
    }
}
