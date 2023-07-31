/**
 * HashCalc.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.hashingAlgorithms;


//Class for computing and checking hashes of strings using various hashing algorithms.
public class HashCalc {
    //Algorithms classes
    private static final Blake2SHashAlgorithm blake2sHashAlgorithm = new Blake2SHashAlgorithm();
    private static final BCryptHashAlgorithm bCryptHashAlgorithm = new BCryptHashAlgorithm();


    private HashCalc() {
    } // No instantiation


    public static String getHash(HashAlgorithms algorithm, String text) {
        GenericHashAlgorithm hashAlgorithm = switch (algorithm) {
            case BLAKE2S -> blake2sHashAlgorithm;
            case BCRYPT -> bCryptHashAlgorithm;
        };

        return hashAlgorithm.getHash(text);
    }

    public static boolean checkHash(HashAlgorithms algorithm, String text, String hash) {
        GenericHashAlgorithm hashAlgorithm = switch (algorithm) {
            case BLAKE2S -> blake2sHashAlgorithm;
            case BCRYPT -> bCryptHashAlgorithm;
        };

        return hashAlgorithm.checkHash(text, hash);
    }
}
