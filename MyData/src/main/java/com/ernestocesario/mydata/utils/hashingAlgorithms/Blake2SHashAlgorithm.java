/**
 * Blake2SHashAlgorithm.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.hashingAlgorithms;

import org.bouncycastle.crypto.digests.Blake2sDigest;

class Blake2SHashAlgorithm implements GenericHashAlgorithm {
    @Override
    public String getHash(String text) {
        byte[] arr = text.getBytes();
        Blake2sDigest blake2s = new Blake2sDigest();
        byte[] hash = new byte[blake2s.getDigestSize()];
        blake2s.update(arr, 0, arr.length);
        blake2s.doFinal(hash, 0);

        return byteToHex(hash);
    }

    @Override
    public boolean checkHash(String text, String hash) {
        return getHash(text).equals(hash);
    }


    //Private functions
    private static String byteToHex(byte[] arr) {
        StringBuilder hexString = new StringBuilder();

        for (byte b : arr) {
            String hexVal = Integer.toHexString(0xff & b);
            if (hexVal.length() == 1)
                hexString.append('0');
            hexString.append(hexVal);
        }

        return hexString.toString();
    }
}
