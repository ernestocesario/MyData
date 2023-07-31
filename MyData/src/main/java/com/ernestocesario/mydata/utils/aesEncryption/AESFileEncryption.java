/**
 * AESFileEncryption.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.aesEncryption;

import com.ernestocesario.mydata.model.constants.AppConstants;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;


////Class used to encrypt and decrypt files with AES 256 using a string of any size as the key, which is then adapted by the utility class AESKeyGenerator
public class AESFileEncryption {
    //Local Constants
    private static final String ALGORITHM = "AES";
    private static final int BUFFER_SIZE = 4096;
    private static final String TMP_FILEPATH = AppConstants.APPDATA_PATH + ".tmp";


    private AESFileEncryption() {
    }

    public static boolean encrypt(String filePath, String password) {
        return processFile(filePath, password, Cipher.ENCRYPT_MODE);
    }

    public static boolean decrypt(String filePath, String password) {
        return processFile(filePath, password, Cipher.DECRYPT_MODE);
    }


    //Private Methods
    private static boolean processFile(String filePath, String password, int cipherMode) {
        try {
            byte[] salt = new byte[AESKeyGenerator.SALT_LENGTH];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));

            if (cipherMode == Cipher.ENCRYPT_MODE)
                salt = AESKeyGenerator.generateSalt();
            else
                bufferedInputStream.read(salt);

            SecretKey secretKey = AESKeyGenerator.generateKey(password, salt);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(cipherMode, secretKey);

            CipherInputStream cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(TMP_FILEPATH));

            if (cipherMode == Cipher.ENCRYPT_MODE)
                bufferedOutputStream.write(salt);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = cipherInputStream.read(buffer)) != -1)
                bufferedOutputStream.write(buffer, 0, bytesRead);

            bufferedOutputStream.close();
            cipherInputStream.close();

            Path originalFile = Path.of(filePath);
            Path encryptedFile = Path.of(TMP_FILEPATH);

            if (!overwriteFile(filePath) || !Files.deleteIfExists(originalFile))
                return false;

            Files.move(encryptedFile, originalFile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean overwriteFile(String filepath) {
        SecureRandom random = new SecureRandom();

        File file = new File(filepath);
        long remainingSize = file.length();

        try (var bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filepath))) {

            byte[] buffer = new byte[BUFFER_SIZE];
            while (remainingSize > 0) {
                if (BUFFER_SIZE > remainingSize)
                    buffer = new byte[(int) remainingSize];
                remainingSize -= buffer.length;
                random.nextBytes(buffer);
                bufferedOutputStream.write(buffer);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
