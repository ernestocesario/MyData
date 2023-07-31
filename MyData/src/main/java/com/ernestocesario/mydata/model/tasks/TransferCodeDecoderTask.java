/**
 * TransferCodeDecoderTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.utils.InetSocketAddressEncoder;
import javafx.concurrent.Task;

import java.net.InetSocketAddress;
import java.util.Objects;

public class TransferCodeDecoderTask extends Task<InetSocketAddress> {
    private final String transferCode;

    public TransferCodeDecoderTask(String transferCode) {
        this.transferCode = Objects.requireNonNull(transferCode);
    }

    @Override
    protected InetSocketAddress call() {
        return InetSocketAddressEncoder.decode(transferCode);
    }
}
