/**
 * AESKeyGenerator.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.aesEncryption;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


//Class used to generate valid keys for AES 256 (used by the utility class AESFileEncryption)
class AESKeyGenerator {
    //Local Constants
    private static final int KEY_SIZE = 256; // Lunghezza della chiave in bit
    private static final int ITERATIONS = 10000; // Numero di iterazioni per PBKDF2
    public static final int SALT_LENGTH = 16; // Lunghezza del sale in byte


    private AESKeyGenerator() {
    }


    public static SecretKey generateKey(String password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_SIZE);
            SecretKey secretKey = factory.generateSecret(spec);

            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }
}
