/**
 * ReceiverFilesTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.OperationResult;
import com.ernestocesario.mydata.utils.OperationStatus;
import com.ernestocesario.mydata.utils.Pair;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import javafx.concurrent.Task;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReceiverFilesTask extends Task<OperationResult<Integer>> {
    //Local Constants
    private static final int BUFFER_SIZE = 4096;
    private static final int TIMEOUT = 10000;


    private final Map<String, Integer> usedFilenames;
    private final String usernameH;
    private final File pathToSave;
    private final InetSocketAddress socketAddress;

    public ReceiverFilesTask(String usernameH, File pathToSave, InetSocketAddress socketAddress) {
        usedFilenames = new HashMap<>();
        this.usernameH = Objects.requireNonNull(usernameH);
        this.pathToSave = Objects.requireNonNull(pathToSave);
        this.socketAddress = Objects.requireNonNull(socketAddress);
    }


    @Override
    protected OperationResult<Integer> call() throws Exception {
        updateProgress(0, 1);
        OperationStatus status;

        usedFilenames.clear();  //clear the map of usedFileNames

        int numFileReceived = 0;
        int numFileToReceive = 0;

        try (Socket socket = new Socket(socketAddress.getHostString(), socketAddress.getPort())) {
            socket.setSoTimeout(TIMEOUT);

            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            numFileToReceive = dataInputStream.readInt();  //num files to receive
            byte[] buffer = new byte[BUFFER_SIZE];

            for (int i = 0; i < numFileToReceive; ++i) {

                String entireFileName = dataInputStream.readUTF();  //entireFileName of current file

                entireFileName = getUniqueFilename(entireFileName);

                long fileSize = dataInputStream.readLong();
                final String filepath = Paths.get(pathToSave.getAbsolutePath(), entireFileName).toString();

                if (fileSize >= 0) {
                    int byteReads;
                    long remaining = fileSize;
                    try (var fileOutputStream = new FileOutputStream(filepath)) {
                        while ((byteReads = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, remaining))) > 0) {
                            fileOutputStream.write(buffer, 0, byteReads);
                            remaining -= byteReads;

                            if (isCancelled())
                                return new OperationResult<>(OperationStatus.CANCELLED, numFileToReceive - numFileReceived);
                        }
                    }
                    Database.getInstance().addSecureFile(usernameH, filepath, FileProtection.Unlocked);  //Adds the file to the database of the user
                    ++numFileReceived;
                }

                updateProgress(i + 1, numFileToReceive);
            }

            status = (numFileToReceive - numFileReceived == 0) ? OperationStatus.SUCCEED : OperationStatus.FAILED;
        } catch (SocketTimeoutException e) {
            status = OperationStatus.NOT_STARTED;
        } catch (Exception e) {
            status = OperationStatus.FAILED;
        }

        return new OperationResult<>(status, numFileToReceive - numFileReceived);
    }


    //Private Functions
    private String getUniqueFilename(String entireFileName) {
        if (!usedFilenames.containsKey(entireFileName))
            usedFilenames.put(entireFileName, 0);

        String uniqueFileName;
        do {
            uniqueFileName = getNextCounteredFileName(entireFileName);
        } while (Files.exists(Paths.get(pathToSave.getAbsolutePath(), uniqueFileName)));

        return uniqueFileName;
    }

    private String getNextCounteredFileName(String entireFileName) {
        int counter = usedFilenames.get(entireFileName);  //number to assign to the current filename
        usedFilenames.put(entireFileName, counter + 1);  //update counter

        if (counter == 0)
            return entireFileName;

        Pair<String, String> splittedName = FileData.splitNameFromExtension(entireFileName);  //split entireFileName in two parts
        String filename = splittedName.first;
        String extension = splittedName.second;

        return filename + " (" + counter + ")" + "." + extension;  //assembles new entireFileName
    }
}
