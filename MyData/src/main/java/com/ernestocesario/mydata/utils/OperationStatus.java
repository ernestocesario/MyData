/**
 * OperationStatus.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;


//Class enum used to represent the current result of a service (used by the OperationResult utility class)
public enum OperationStatus {
    NOT_STARTED,
    CANCELLED,
    FAILED,
    WORKING,
    SUCCEED
}
