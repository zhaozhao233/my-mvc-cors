package com.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Main {
    public static void main(String[] args) {
        bcryptPasswordEncoderDemo();
    }

    private static void bcryptPasswordEncoderDemo() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("123");
        System.out.println("-----debug: resutl = "+result);
    }
}
