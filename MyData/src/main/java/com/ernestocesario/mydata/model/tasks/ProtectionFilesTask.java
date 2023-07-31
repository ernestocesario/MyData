/**
 * ProtectionFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.aesEncryption.AESFileEncryption;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Objects;

public class ProtectionFilesTask extends Task<Integer> {
    private final String password;
    private final FileProtection newProtection;
    private final ObservableList<FileData> itemsOfTable;
    private final ObservableList<Integer> indices;

    public ProtectionFilesTask(String password, FileProtection newProtection, ObservableList<FileData> itemsOfTable, ObservableList<Integer> indices) {
        this.password = Objects.requireNonNull(password);
        this.newProtection = Objects.requireNonNull(newProtection);
        this.itemsOfTable = Objects.requireNonNull(itemsOfTable);
        this.indices = Objects.requireNonNull(indices);
    }

    @Override
    protected Integer call() throws Exception {
        int numFilesCannotModify = 0;

        updateProgress(0, indices.size());
        for (int i = 0; i < indices.size(); ++i) {
            int index = indices.get(i);
            FileData fileData = itemsOfTable.get(index);

            if (!fileData.getProtection().equals(newProtection))
                switch (newProtection) {
                    case Locked -> {
                        if (!encryptFile(fileData.getPath(), password))
                            ++numFilesCannotModify;
                    }
                    case Unlocked -> {
                        if (!decryptFile(fileData.getPath(), password))
                            ++numFilesCannotModify;
                    }
                }

            updateProgress(i + 1, indices.size());
        }

        return numFilesCannotModify;
    }


    public static boolean encryptFile(String filepath, String password) {
        if (AESFileEncryption.encrypt(filepath, password)) {
            Database.getInstance().updateFileProtection(filepath, FileProtection.Locked);
            return true;
        }
        return false;
    }

    public static boolean decryptFile(String filepath, String password) {
        if (AESFileEncryption.decrypt(filepath, password)) {
            Database.getInstance().updateFileProtection(filepath, FileProtection.Unlocked);
            return true;
        }
        return false;
    }
}
