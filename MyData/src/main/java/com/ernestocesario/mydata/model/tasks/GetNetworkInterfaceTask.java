/**
 * GetNetworkInterfaceTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetNetworkInterfaceTask extends Task<ObservableList<NetworkInterface>> {
    @Override
    protected ObservableList<NetworkInterface> call() throws Exception {
        updateProgress(0, 1);
        ObservableList<NetworkInterface> activeInterfaces = FXCollections.observableArrayList();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isUp())
                    activeInterfaces.add(networkInterface);
            }
            updateProgress(1, 1);
        } catch (SocketException e) {
            return null;
        }

        return activeInterfaces;
    }
}
