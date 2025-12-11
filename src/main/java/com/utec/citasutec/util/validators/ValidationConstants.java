package com.utec.citasutec.util.validators;

public final class ValidationConstants {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_NAME_LENGTH = 4;
    public static final String RESOURCE_PATTERN = "^[a-zA-Z0-9_-]+$";
    public static final int MIN_SERVICE_NAME_LENGTH = 4;
    public static final int MAX_SERVICE_NAME_LENGTH = 250;

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }

}
