/**
 * TableUpdaterService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.TableUpdaterTask;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

//Service to update the list of all the files of the current account
public class TableUpdaterService extends Service<List<FileData>> {
    private final String usernameH;

    public TableUpdaterService(String usernameH) {
        this.usernameH = usernameH;
    }

    @Override
    protected Task<List<FileData>> createTask() {
        return new TableUpdaterTask(usernameH);
    }
}
