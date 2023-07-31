/**
 * OperationResult.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;


//Class record representing the result of a service operation (used by SenderFilesService and ReceiverFilesService)
public record OperationResult<T>(OperationStatus status, T result) {
    public OperationResult(OperationStatus status) {
        this(status, null);
    }
}
