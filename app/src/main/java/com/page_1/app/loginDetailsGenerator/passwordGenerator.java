package com.page_1.app.loginDetailsGenerator;

import java.util.Random;

public class passwordGenerator {
    public char[] password() {
        int length = 6;
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

}
