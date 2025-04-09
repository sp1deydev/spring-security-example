package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BcryptUtils {
    protected static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(8);

    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public static boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
