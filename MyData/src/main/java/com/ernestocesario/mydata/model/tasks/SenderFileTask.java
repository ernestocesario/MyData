/**
 * SenderFileTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.utils.OperationResult;
import com.ernestocesario.mydata.utils.OperationStatus;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class SenderFileTask extends Task<OperationResult<Integer>> {
    //Local Constants
    private static final int BUFFER_SIZE = 4096;

    private final InetSocketAddress inetSocketAddress;
    private final String password;
    private final ObservableList<FileData> itemsOfTable;
    private final ObservableList<Integer> indices;

    public SenderFileTask(InetSocketAddress inetSocketAddress, String password, ObservableList<FileData> items, ObservableList<Integer> indices) {
        this.inetSocketAddress = Objects.requireNonNull(inetSocketAddress);
        this.password = Objects.requireNonNull(password);
        this.itemsOfTable = Objects.requireNonNull(items);
        this.indices = Objects.requireNonNull(indices);
    }

    @Override
    protected OperationResult<Integer> call() throws Exception {
        updateProgress(0, 1);
        OperationStatus status;
        int numFileSent = 0;
        final int numFileToSend = indices.size();

        try (ServerSocket serverSocket = new ServerSocket(inetSocketAddress.getPort(), 0)) {
            try (Socket socket = serverSocket.accept()) {
                status = OperationStatus.WORKING;
                updateValue(new OperationResult<>(status));  //to close the popup

                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                dataOutputStream.writeInt(numFileToSend);  //sends number of files to receive
                dataOutputStream.flush();

                byte[] buffer = new byte[BUFFER_SIZE];

                for (int i = 0; i < indices.size(); ++i) {
                    int index = indices.get(i);
                    FileData fileData = itemsOfTable.get(index);

                    if (!unlockFile(fileData))
                        continue;

                    String entireFileName = fileData.getEntireName();
                    long fileSize = FileData.getRealSize(fileData.getPath());

                    dataOutputStream.writeUTF(entireFileName);  //sends entireFilename of the current file
                    dataOutputStream.writeLong(fileSize);  //send size of the current file

                    try (FileInputStream fileInputStream = new FileInputStream(fileData.getPath())) {
                        int bytesRead;
                        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                            dataOutputStream.write(buffer, 0, bytesRead);  //sends portion of BUFFER_SIZE bytes of file

                            if (isCancelled())
                                return new OperationResult<>(OperationStatus.CANCELLED, numFileToSend - numFileSent);
                        }

                        dataOutputStream.flush();
                        ++numFileSent;
                    } catch (FileNotFoundException | SecurityException ignore) {
                        //if the file was deleted
                    }

                    updateProgress(i + 1, indices.size());
                }

                status = (numFileToSend - numFileSent == 0) ? OperationStatus.SUCCEED : OperationStatus.FAILED;
            }
        } catch (Exception e) {
            updateProgress(0, 1);
            status = OperationStatus.FAILED;
            updateValue(new OperationResult<>(status));  //per chiudere la finestra di popup send
        }

        int result = numFileToSend - numFileSent;
        return new OperationResult<>(status, result);
    }


    //Private Functions
    private boolean unlockFile(FileData fileData) {
        if (fileData.getProtection() != FileProtection.Locked)
            return true;

        if (!Settings.getInstance().getAutoUnlockFilesToSend())
            return false;

        return ProtectionFilesTask.decryptFile(fileData.getPath(), password);
    }
}
