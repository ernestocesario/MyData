/**
 * SingleInstanceChecker.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;

import com.ernestocesario.mydata.model.constants.AppConstants;
import com.ernestocesario.mydata.model.messages.GeneralMessages;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.scene.control.Alert;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/*
Class that is loaded when the application starts.
It checks that only one instance of the app can run, so as not to create race condition on the database and settings files.
 */
public class SingleInstanceChecker {
    private static final SingleInstanceChecker instance = new SingleInstanceChecker();
    private RandomAccessFile randomAccessFile;
    private FileChannel channel;
    private FileLock lock;


    public static void initialize() {
    }  //IDH

    private SingleInstanceChecker() {
        try {
            randomAccessFile = new RandomAccessFile(AppConstants.SINGLE_INSTANCE_CHECKER_FILE_PATH, "rw");
            channel = randomAccessFile.getChannel();
            lock = channel.tryLock();
            if (lock != null)
                return;
        } catch (Exception ignore) {
        }

        ViewSelector.getInstance().createAlert(GeneralMessages.APP_ALREADY_OPENED, Alert.AlertType.WARNING).showAndWait();
        System.exit(0);
    }
}
