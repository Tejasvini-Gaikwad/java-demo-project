package com.example.assetmanagementsystem.constants;

import io.micrometer.common.util.StringUtils;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ErrorConstantString {
    private ErrorConstantString() {
        throw new IllegalStateException("Error Constant String Utility Class");
    }

    public static String notFoundErrorMessage(String language, String text) {
        final Map<String, Map<String, String>> errorMessage = new HashMap<>();
        Map<String, String> englishMessages = new HashMap<>();
        englishMessages.put(AppConstants.USER, "User not found");


        errorMessage.put(AppConstants.ENGLISH, englishMessages);

        if (StringUtils.isEmpty(language)) language = AppConstants.ENGLISH;
        Map<String, String> messages = errorMessage.getOrDefault(language.toLowerCase(), errorMessage.get(AppConstants.ENGLISH));
        return messages.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
    }

    public static String failed(String language, String text) {
            return MessageFormat.format(ErrorConstants.FAILED, text);
    }

//    public static String deactivatedUser(String language) {
//            return ErrorConstants.DEACTIVATED_USER;
//    }
//
//    public static String deletedUser(String language) {
//            return ErrorConstants.DELETED_USER;
//    }

//    public static String encryptionErrorMessage(String language) {
//            return ErrorConstants.ENCRYPTION_ERROR_MESSAGE;
//    }
//
//    public static String decryptionErrorMessage() {
//        return ErrorConstants.DECRYPTION_ERROR_MESSAGE;
//    }

    public static String loginUserNotFoundErrorMessage(String language) {
            return ErrorConstants.LOGIN_USER_NOT_FOUND_ERROR_MESSAGE;
    }

    public static String notValidErrorMessage(String text) {
        final Map<String, Map<String, String>> errorMessage = new HashMap<>();
        Map<String, String> englishMessages = new HashMap<>();
        englishMessages.put(AppConstants.PHONE, "Phone Number is not valid");
        englishMessages.put(AppConstants.EMAIL, "Email is not valid");
        englishMessages.put(AppConstants.PASSWORD, "Password is not valid");
        errorMessage.put(AppConstants.ENGLISH, englishMessages);
        Map<String, String> messages = errorMessage.getOrDefault(AppConstants.ENGLISH.toLowerCase(), errorMessage.get(AppConstants.ENGLISH));
        return messages.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
    }

    public static String notValidErrorMessageGroup(String language, String code) {
            return MessageFormat.format(ErrorConstants.NOT_VALID_ERROR_MESSAGE, code);
    }

//    public static String notValidErrorMessageRecordCount(String language, String count) {
//            return MessageFormat.format(ErrorConstants.NOT_VALID_FILE_RECORD_COUNT_MESSAGE, count);
//    }
//
//    public static String notValidErrorMessageFileSize(String language, String fileSize) {
//            return MessageFormat.format(ErrorConstants.NOT_VALID_FILE_SIZE_MESSAGE, fileSize);
//    }

//    public static String notValidErrorMessageFileType(String language, String fileTypes) {
//            return MessageFormat.format(ErrorConstants.NOT_VALID_FILE_TYPE_MESSAGE, fileTypes);
//    }
//
//    public static String notValidErrorMessageUserRole(String language) {
//            return ErrorConstants.NOT_VALID_USER_ROLE_MESSAGE;
//    }

    public static String notValidErrorMessagePassword(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_PASSWORD;
    }

    public static String notValidErrorMessageChangePassword(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_CHANGE_PASSWORD;
    }

    public static String notMatchErrorMessage(String first, String second) {
        return MessageFormat.format(ErrorConstants.NOT_MATCH_ERROR_MESSAGE, first, second);
    }

    public static String mandatoryErrorMessage(String text) {
        final Map<String, Map<String, String>> errorMessage = new HashMap<>();

        Map<String, String> englishMessages = new HashMap<>();
        englishMessages.put(AppConstants.DESCRIPTION, "Description is mandatory");
        englishMessages.put(AppConstants.PHONE, "Phone number is mandatory");
        englishMessages.put(AppConstants.NAME, "Name is mandatory");
        englishMessages.put(AppConstants.USERNAME, "User Name is mandatory");
        englishMessages.put(AppConstants.PASSWORD, "Password is mandatory");
        englishMessages.put(AppConstants.FIRST_NAME, "First Name is mandatory");
        englishMessages.put(AppConstants.LAST_NAME, "Last Name is mandatory");
        englishMessages.put(AppConstants.EMAIL, "Email is mandatory");
        errorMessage.put(AppConstants.ENGLISH, englishMessages);
        Map<String, String> messages = errorMessage.getOrDefault(AppConstants.ENGLISH.toLowerCase(), errorMessage.get(AppConstants.ENGLISH));
        return messages.getOrDefault(text, AppConstants.UNKNOWN_ERROR);
    }

    public static String alreadyPresentErrorMessage(String text, String var2) {
        final Map<String, Map<String, String>> errorMessage = new HashMap<>();
        Map<String, String> englishMessages = new HashMap<>();
        englishMessages.put(AppConstants.NAME, "Name: {0} already exists");
        englishMessages.put(AppConstants.USERNAME, "User Name: {0} already exists");
        errorMessage.put(AppConstants.ENGLISH, englishMessages);
        Map<String, String> messages = errorMessage.getOrDefault(AppConstants.ENGLISH.toLowerCase(), errorMessage.get(AppConstants.ENGLISH));
        return MessageFormat.format(messages.getOrDefault(text, AppConstants.UNKNOWN_ERROR), var2);
    }

    public static String alreadyLoggedInErrorMessage(String language) {
            return ErrorConstants.ALREADY_LOGGED_IN_ERROR_MESSAGE;
    }

    public static String jwtTokenExpiredErrorMessage(String language) {
            return ErrorConstants.JWT_TOKEN_EXPIRED_ERROR_MESSAGE;
    }

    public static String unauthorizedUser(String language) {
            return ErrorConstants.UNAUTHORIZED_USER;
    }

    public static String notValidErrorMessageUsername(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_USERNAME;
    }

//    public static String currentPasswordIncorrect(String language) {
//            return ErrorConstants.CURRENT_PASSWORD_INCORRECT;
//    }

    public static String notValidErrorMessageFirstname(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_FIRSTNAME;
    }

    public static String notValidErrorMessageLastname(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_LASTNAME;
    }

    public static String notValidErrorMessagePhone(String language) {
            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_PHONE;
    }

//    public static String notValidErrorMessageUserDorakuCode(String language) {
//            return ErrorConstants.NOT_VALID_ERROR_MESSAGE_USER_DORAKU_CODE;
//    }
//
//    public static String updateUserStatusErrorMessage(String language) {
//            return ErrorConstants.INACTIVE_USER_ERROR_MESSAGE;
//    }
}
