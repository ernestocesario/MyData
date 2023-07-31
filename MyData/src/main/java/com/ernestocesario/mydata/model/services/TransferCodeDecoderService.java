/**
 * TransferCodeDecoderService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.TransferCodeDecoderTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.InetSocketAddress;

//Service to decode a hexstring into an InetSocketAddress (uses the InetSocketAddressEncoder utility class)
public class TransferCodeDecoderService extends Service<InetSocketAddress> {
    private String transferCode;

    public TransferCodeDecoderService() {
    }

    public void setTransferCode(String transferCode) {
        this.transferCode = transferCode;
    }

    @Override
    protected Task<InetSocketAddress> createTask() {
        return new TransferCodeDecoderTask(transferCode);
    }
}
