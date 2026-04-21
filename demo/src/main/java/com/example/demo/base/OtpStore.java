package com.example.demo.base;

import java.util.concurrent.ConcurrentHashMap;

public class OtpStore {

    private static class OtpEntry {
        String code;
        long expiryTime;

        OtpEntry(String code, long expiryTime) {
            this.code = code;
            this.expiryTime = expiryTime;
        }
    }

    private static final ConcurrentHashMap<String, OtpEntry> otpMap = new ConcurrentHashMap<>();

    private static final long EXPIRY_DURATION = 5 * 60 * 1000; // 5 minutes

    public static void put(String email, String code) {
        long expiry = System.currentTimeMillis() + EXPIRY_DURATION;
        otpMap.put(email, new OtpEntry(code, expiry));
    }

    public static boolean verify(String email, String inputCode) {
        OtpEntry entry = otpMap.get(email);

        if (entry == null) return false;

        // Check expiration
        if (System.currentTimeMillis() > entry.expiryTime) {
            otpMap.remove(email);
            return false;
        }

        // Check code
        if (entry.code.equals(inputCode)) {
            otpMap.remove(email); // DELETE after success
            return true;
        }

        return false;
    }

    public static String get(String email) {
        OtpEntry entry = otpMap.get(email);
        return entry != null ? entry.code : null;
    }

    public static boolean exists(String email) {
        return otpMap.containsKey(email);
    }
}