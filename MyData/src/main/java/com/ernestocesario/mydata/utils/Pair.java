/**
 * Pair.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;


public class Pair<T, K> {
    public T first;
    public K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
        this(null, null);
    }
}
