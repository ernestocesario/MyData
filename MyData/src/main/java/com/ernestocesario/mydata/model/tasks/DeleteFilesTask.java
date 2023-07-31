/**
 * DeleteFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.util.Objects;

public class DeleteFilesTask extends Task<Integer> {
    private final ObservableList<FileData> itemsOfTable;
    private final ObservableList<Integer> indices;

    public DeleteFilesTask(ObservableList<FileData> itemsOfTable, ObservableList<Integer> indices) {
        this.itemsOfTable = Objects.requireNonNull(itemsOfTable);
        this.indices = Objects.requireNonNull(indices);
    }

    @Override
    protected Integer call() {
        updateProgress(0, indices.size());
        int numFileCannotDelete = 0;
        for (int i = 0; i < indices.size(); ++i) {
            int index = indices.get(i);
            FileData fileData = itemsOfTable.get(index);

            File file = new File(fileData.getPath());

            if (file.delete())
                Database.getInstance().removeSecureFileByPath(fileData.getPath());
            else
                ++numFileCannotDelete;

            updateProgress(i + 1, indices.size());
        }

        return numFileCannotDelete;
    }
}
