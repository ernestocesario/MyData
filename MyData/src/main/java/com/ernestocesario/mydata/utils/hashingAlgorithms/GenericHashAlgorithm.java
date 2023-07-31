/**
 * GenericHashAlgorithm.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.hashingAlgorithms;


//Interface implemented by all classes that do compute hashes with a particular algorithm
interface GenericHashAlgorithm {
    String getHash(String text);

    boolean checkHash(String text, String hash);
}
