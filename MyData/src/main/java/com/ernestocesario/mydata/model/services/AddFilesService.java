/**
 * AddFilesService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.AddFilesTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

//Service to add files to current account
public class AddFilesService extends Service<Integer> {
    private final String usernameH;
    private List<File> files;

    public AddFilesService(String usernameH) {
        this.usernameH = usernameH;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    protected Task<Integer> createTask() {
        return new AddFilesTask(usernameH, files);
    }
}
