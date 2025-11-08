package com.utec.citasutec.util.formatters;

import java.security.SecureRandom;

public class Obfuscator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final int OBFUSCATION_LENGTH = 8;

    public static String obfuscate(String input) {
        StringBuilder obfuscated = new StringBuilder(input);
        for (int i = 0; i < OBFUSCATION_LENGTH; i++) {
            char randomChar = CHARSET[RANDOM.nextInt(CHARSET.length)];
            obfuscated.append(randomChar);
        }
        return obfuscated.toString();
    }

    /**
     * Obfuscates the local part of an email address by replacing characters between the first two
     * and the '@' symbol with random characters, while leaving the domain intact.
     *
     * @param email the email address to obfuscate
     * @return the obfuscated email address, or the original email if it is invalid or too short
     */
    public static String obfuscateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return email;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return email;
        }

        if (atIndex < 2) {
            return email;
        }

        char[] result = new char[email.length()];

        // Copy the first two characters if available
        if (email.length() > 0) {
            result[0] = email.charAt(0);
        }
        if (email.length() > 1) {
            result[1] = email.charAt(1);
        }

        for (int i = 2; i <= atIndex - 1; i++) {
            result[i] = getRandomChar();
        }
        
        result[0] = email.charAt(0);
        result[1] = email.charAt(1);

        for (int i = 2; i <= atIndex - 1; i++) {
            result[i] = getRandomChar();
        }

        result[atIndex] = '@';

        // Copy the domain part as-is
        for (int i = atIndex + 1; i < result.length; i++) {
            result[i] = email.charAt(i);
        }

        return new String(result);
    }

    private static char getRandomChar() {
        return CHARSET[RANDOM.nextInt(CHARSET.length)];
    }
}
