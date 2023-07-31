/**
 * TableUpdaterTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.utils.Pair;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import com.ernestocesario.mydata.utils.fileSpecs.FileSizeRepresentation;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableUpdaterTask extends Task<List<FileData>> {
    private final String usernameH;

    public TableUpdaterTask(String usernameH) {
        this.usernameH = Objects.requireNonNull(usernameH);
    }

    @Override
    protected List<FileData> call() throws Exception {
        List<FileData> fileList = new ArrayList<>();

        FileSizeRepresentation sizeRepresentation = Settings.getInstance().getFileSizeRepresentation();
        int decimalDigitsRound = Settings.getInstance().getFileSizeRoundDecimalDigits();

        Pair<List<String>, List<String>> filesStuff = Database.getInstance().getSecureFilesByUser(usernameH);
        List<String> filePaths = filesStuff.first;
        List<String> fileProtections = filesStuff.second;

        if (filePaths.size() != fileProtections.size())  //
            return fileList;

        updateProgress(0, filePaths.size());
        for (int i = 0; i < filePaths.size(); ++i) {
            String path = filePaths.get(i);
            FileProtection protection = FileProtection.valueOf(fileProtections.get(i));
            FileData fileData = FileData.getFileDataFromPath(path, protection, sizeRepresentation, decimalDigitsRound);

            if (fileData == null)
                Database.getInstance().removeSecureFileByPath(path);
            else
                fileList.add(fileData);
            updateProgress(i + 1, filePaths.size());
        }

        return fileList;
    }
}
