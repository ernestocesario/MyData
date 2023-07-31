/**
 * SecureRandomStringGenerator.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;


import java.security.SecureRandom;


//Class to create secure random strings of visible ASCII characters.
public class SecureRandomStringGenerator {
    //Local constants
    private static final char MIN_CHAR = 33;
    private static final char MAX_CHAR = 126;


    private SecureRandomStringGenerator() {
    }  //no instantiation


    public static String gen(int length) {
        return gen(length, length);
    }

    public static String gen(int minLen, int maxLen) {
        SecureRandom secureRandom = new SecureRandom();
        int stringLength = secureRandom.nextInt(minLen, maxLen + 1);

        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; ++i) {
            char randomChar = (char) secureRandom.nextInt(MIN_CHAR, MAX_CHAR + 1);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
