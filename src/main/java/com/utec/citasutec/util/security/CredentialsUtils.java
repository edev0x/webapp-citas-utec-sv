package com.utec.citasutec.util.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class CredentialsUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private CredentialsUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
