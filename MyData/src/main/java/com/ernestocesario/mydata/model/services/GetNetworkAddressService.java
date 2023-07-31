/**
 * GetNetworkAddressService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.GetNetworkAddressTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;

//Service to obtain an InetSocketAddress containing an IPv4 or IPv6 of the network interface passed, and a random port
public class GetNetworkAddressService extends Service<InetSocketAddress> {
    private NetworkInterface networkInterface = null;

    public GetNetworkAddressService() {
    }

    public void setNetworkInterface(NetworkInterface networkInterface) {
        this.networkInterface = networkInterface;
    }

    @Override
    protected Task<InetSocketAddress> createTask() {
        return new GetNetworkAddressTask(networkInterface);
    }
}
