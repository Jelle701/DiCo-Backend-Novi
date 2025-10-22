package com.example_jelle.backenddico.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashUtil {

    /**
     * Calculates the SHA-256 hash of a given text.
     * This is required by the LibreView API to generate the Account-Id header.
     *
     * @param text The input string to hash.
     * @return The SHA-256 hashed string, represented in hexadecimal format.
     */
    public String calculateSha256(String text) {
        if (text == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // This should never happen as SHA-256 is a standard algorithm
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
