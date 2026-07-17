package com.lodge_treasury.management.constants;

public final class LodgeConstants {

    private LodgeConstants() {
        // Prevent instantiation
    }

    // Status codes
    public static final String STATUS_201 = "201";
    public static final String STATUS_200 = "200";
    public static final String STATUS_400 = "400";
    public static final String STATUS_409 = "409";
    public static final String STATUS_500 = "500";

    // Success messages
    public static final String MESSAGE_201 = "Mason registered successfully";
    public static final String MESSAGE_200 = "Request processed successfully";

    // Error messages (you can use them in exceptions or responses)
    public static final String ERROR_EMAIL_EXISTS = "Email already registered by another mason";
    public static final String ERROR_MOBILE_EXISTS = "Mobile number already registered";
    public static final String ERROR_MASON_NOT_FOUND = "Mason not found with ID: ";
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact support.";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact support.";
    public static final String MESSAGE_500 = "An internal error occurred. Please try again later.";
}