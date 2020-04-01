package com.jedreck.oauth2.server.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class aaa {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("secret"));
    }
}
