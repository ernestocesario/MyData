/**
 * OpenFilesService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.OpenFilesTask;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to open files of the current account
public class OpenFilesService extends Service<Integer> {
    private final String password;
    private ObservableList<FileData> itemsOfTable;
    private ObservableList<Integer> indices;


    public OpenFilesService(String password) {
        this.password = password;
    }

    public void setItemsOfTable(ObservableList<FileData> itemsOfTable) {
        this.itemsOfTable = itemsOfTable;
    }

    public void setIndices(ObservableList<Integer> indices) {
        this.indices = indices;
    }

    @Override
    protected Task<Integer> createTask() {
        return new OpenFilesTask(password, itemsOfTable, indices);
    }
}
