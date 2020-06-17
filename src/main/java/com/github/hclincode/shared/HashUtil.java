package com.github.hclincode.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class HashUtil {
    // Skip 1, 0, l, O to avoid user's confusion
    private static final String RANDOM_HASH_CHARS = "23456789abcdefghijkmnopqrstuwxyzABCDEFGHIJKLMNPQRSTUVWXYZ-_";
    private static final String VALID_HASH_CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();

    public String generateUrlHash(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_HASH_CHARS.charAt(RANDOM.nextInt(RANDOM_HASH_CHARS.length())));
        }

        return new String(sb);
    }

    public Boolean isValidUrlHash(String urlHash) {
        return urlHash.chars().parallel().allMatch(i -> VALID_HASH_CHARS.indexOf(i) >= 0);
    }
}