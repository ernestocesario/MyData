/**
 * DeleteFilesService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.DeleteFilesTask;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to delete files to current account
public class DeleteFilesService extends Service<Integer> {
    private ObservableList<FileData> itemsOfTable;
    private ObservableList<Integer> indices;


    public DeleteFilesService() {
    }

    public void setItemsOfTable(ObservableList<FileData> itemsOfTable) {
        this.itemsOfTable = itemsOfTable;
    }

    public void setIndices(ObservableList<Integer> indices) {
        this.indices = indices;
    }

    @Override
    protected Task<Integer> createTask() {
        return new DeleteFilesTask(itemsOfTable, indices);
    }
}
