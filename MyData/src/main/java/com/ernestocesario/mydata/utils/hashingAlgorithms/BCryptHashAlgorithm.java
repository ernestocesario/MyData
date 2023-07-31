/**
 * BCryptHashAlgorithm.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.hashingAlgorithms;

import org.springframework.security.crypto.bcrypt.BCrypt;

class BCryptHashAlgorithm implements GenericHashAlgorithm {
    //Local Constants
    private final int BCRYPT_LOG_ROUND = 15;

    @Override
    public String getHash(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt(BCRYPT_LOG_ROUND));
    }

    @Override
    public boolean checkHash(String text, String hash) {
        return BCrypt.checkpw(text, hash);
    }
}
