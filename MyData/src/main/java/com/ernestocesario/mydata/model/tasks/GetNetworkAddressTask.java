/**
 * GetNetworkAddressTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.*;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Objects;

public class GetNetworkAddressTask extends Task<InetSocketAddress> {
    //Local Constants
    private final int DEFAULT_FROM_PORT = 49152;
    private final int DEFAULT_TO_PORT = 65535;


    private final NetworkInterface networkInterface;

    public GetNetworkAddressTask(NetworkInterface networkInterface) {
        this.networkInterface = Objects.requireNonNull(networkInterface);
    }

    @Override
    protected InetSocketAddress call() {
        updateProgress(0, 1);
        String ip = getValidIpFromInterface(networkInterface);
        if (ip == null)
            return null;

        Integer port = getFreePort();
        if (port == null)
            return null;

        updateProgress(1, 1);
        return new InetSocketAddress(ip, port);
    }


    //Private Methods
    private String getValidIpFromInterface(NetworkInterface networkInterface) {
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

        while (addresses.hasMoreElements()) {
            InetAddress address = addresses.nextElement();

            if (address instanceof Inet4Address || address instanceof Inet6Address)
                return address.getHostAddress();
        }
        return null;
    }

    private Integer getFreePort() {
        LinkedList<Integer> validPorts = getValidPorts();
        SecureRandom random = new SecureRandom();

        while (validPorts.size() > 0) {
            int indexPort = random.nextInt(0, validPorts.size());
            int port = validPorts.get(indexPort);

            try (var ignore = new ServerSocket(port)) {
                return port;
            } catch (IOException e) {
                validPorts.remove(indexPort);
            }
        }
        return null;
    }

    private LinkedList<Integer> getValidPorts() {
        LinkedList<Integer> validPorts = new LinkedList<>();

        for (int i = DEFAULT_FROM_PORT; i <= DEFAULT_TO_PORT; ++i)
            validPorts.add(i);

        return validPorts;
    }
}
