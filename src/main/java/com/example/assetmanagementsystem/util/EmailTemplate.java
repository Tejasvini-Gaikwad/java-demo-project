package com.example.assetmanagementsystem.util;

public class EmailTemplate {
    EmailTemplate(){
        throw new IllegalStateException("Email Template Utility class");
    }

    public static final String USER_ONBOARDING_SUBJECT = "Welcome to Asset Management";

    public static final String USER_ONBOARDING_SUCCESS = "<h4>Hi {0} {1},</h4>" +
            "<p style=\"margin-left: 20px;\">Welcome Aboard!</p>" +
            "<p style=\"margin-left: 20px;\">Here are your onboarding details:</p>" +
            "<p style=\"margin-left: 20px;\">Username: {2}</p>" +
            "<p style=\"margin-left: 20px;\">Password: {3}</p>" +
            "<p style=\"margin-left: 20px;\">Note: This is an auto-generated message. Please do not reply to this message.</p>"+
            "<p style=\"margin-left: 20px;\">For any queries, please email us at support@asset.com</p>" +
            "<p style=\"margin-bottom: 10px;\">Best Wishes,</p>" +
            "<p style=\"margin-bottom: 10px;\">Asset Management Team </p>";
}
