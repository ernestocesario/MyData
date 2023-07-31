/**
 * OpenFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class OpenFilesTask extends Task<Integer> {
    private final String password;
    private final ObservableList<FileData> itemsOfTable;
    private final ObservableList<Integer> indices;

    public OpenFilesTask(String password, ObservableList<FileData> itemsOfTable, ObservableList<Integer> indices) {
        this.password = Objects.requireNonNull(password);
        this.itemsOfTable = Objects.requireNonNull(itemsOfTable);
        this.indices = Objects.requireNonNull(indices);
    }

    @Override
    protected Integer call() {
        int numFilesCannotOpen = 0;

        if (!Desktop.isDesktopSupported()) {
            updateProgress(indices.size(), indices.size());
            return indices.size();
        }

        updateProgress(0, indices.size());
        for (int i = 0; i < indices.size(); ++i) {
            int index = indices.get(i);
            FileData fileData = itemsOfTable.get(index);

            FileProtection initialFileProtection = fileData.getProtection();

            //if the file is locked, we first unlock it if settings is configured to do so
            if (initialFileProtection == FileProtection.Locked)
                if (Settings.getInstance().getAutoUnlockFilesToOpen() && ProtectionFilesTask.decryptFile(fileData.getPath(), password))
                    fileData.setProtection(FileProtection.Unlocked);

            if (fileData.getProtection() == FileProtection.Unlocked) {
                File file = new File(fileData.getPath());
                try {
                    Desktop.getDesktop().open(file);
                } catch (Exception e) {
                    ++numFilesCannotOpen;
                    //if we cannot open the file and the file was locked, we relock it
                    if (initialFileProtection == FileProtection.Locked)
                        ProtectionFilesTask.encryptFile(fileData.getPath(), password);
                }
            } else
                ++numFilesCannotOpen;

            updateProgress(i + 1, indices.size());
        }

        return numFilesCannotOpen;
    }
}
