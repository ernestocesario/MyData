/**
 * SenderFilesService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.SenderFileTask;
import com.ernestocesario.mydata.utils.OperationResult;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.InetSocketAddress;

//Service to send files from the current account to another device
public class SenderFilesService extends Service<OperationResult<Integer>> {  //Server

    private InetSocketAddress inetSocketAddress;
    private final String password;
    private ObservableList<FileData> itemsOfTable;
    private ObservableList<Integer> indices;


    public SenderFilesService(String password) {
        this.password = password;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public void setItemsOfTable(ObservableList<FileData> itemsOfTable) {
        this.itemsOfTable = itemsOfTable;
    }

    public void setIndices(ObservableList<Integer> indices) {
        this.indices = indices;
    }


    @Override
    protected Task<OperationResult<Integer>> createTask() {
        return new SenderFileTask(inetSocketAddress, password, itemsOfTable, indices);
    }
}
