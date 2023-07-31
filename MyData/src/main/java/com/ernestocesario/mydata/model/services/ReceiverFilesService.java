/**
 * ReceiverFilesService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.ReceiverFilesTask;
import com.ernestocesario.mydata.utils.OperationResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.net.InetSocketAddress;

//Service to receive files from other devices
public class ReceiverFilesService extends Service<OperationResult<Integer>> {  //Client

    private final String usernameH;
    private File pathToSave = null;
    private InetSocketAddress socketAddress;

    public ReceiverFilesService(String usernameH) {
        this.usernameH = usernameH;
    }

    public void setPathToSave(File pathToSave) {
        this.pathToSave = pathToSave;
    }

    public void setInetSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    @Override
    protected Task<OperationResult<Integer>> createTask() {
        return new ReceiverFilesTask(usernameH, pathToSave, socketAddress);
    }
}