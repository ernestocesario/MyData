/**
 * RemoveFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Objects;

public class RemoveFilesTask extends Task<Integer> {
    private final ObservableList<FileData> itemsOfTable;
    private final ObservableList<Integer> indices;

    public RemoveFilesTask(ObservableList<FileData> itemsOfTable, ObservableList<Integer> indices) {
        this.itemsOfTable = Objects.requireNonNull(itemsOfTable);
        this.indices = Objects.requireNonNull(indices);
    }

    @Override
    protected Integer call() throws Exception {
        int numFileLocked = 0;

        updateProgress(0, indices.size());
        for (int i = 0; i < indices.size(); ++i) {
            int index = indices.get(i);

            FileData fileData = itemsOfTable.get(index);
            if (fileData.getProtection() == FileProtection.Locked)
                ++numFileLocked;
            else
                Database.getInstance().removeSecureFileByPath(fileData.getPath());

            updateProgress(i + 1, indices.size());
        }
        return numFileLocked;
    }
}
