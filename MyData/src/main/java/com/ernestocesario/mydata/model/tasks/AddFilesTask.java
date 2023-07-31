/**
 * AddFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AddFilesTask extends Task<Integer> {
    private final String usernameH;
    private final List<File> files;

    public AddFilesTask(String usernameH, List<File> files) {
        this.usernameH = Objects.requireNonNull(usernameH);
        this.files = Objects.requireNonNull(files);
    }

    @Override
    protected Integer call() {
        updateProgress(0, files.size());

        int numFileAlreadyIn = 0;

        for (int i = 0; i < files.size(); ++i) {
            File file = files.get(i);
            boolean fileAlreadyIn = Database.getInstance().checkFileAlreadyIn(file.getAbsolutePath());

            if (!fileAlreadyIn)
                Database.getInstance().addSecureFile(usernameH, file.getAbsolutePath(), FileProtection.Unlocked);
            else
                ++numFileAlreadyIn;

            updateProgress(i + 1, files.size());
        }
        return numFileAlreadyIn;
    }
}
