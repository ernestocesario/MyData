/**
 * GetNetworkInterfaceService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.GetNetworkInterfaceTask;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.NetworkInterface;

//Service to get all the network interface available on the device
public class GetNetworkInterfaceService extends Service<ObservableList<NetworkInterface>> {
    @Override
    protected Task<ObservableList<NetworkInterface>> createTask() {
        return new GetNetworkInterfaceTask();
    }
}
