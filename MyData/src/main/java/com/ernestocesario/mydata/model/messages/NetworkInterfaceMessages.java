/**
 * NetworkInterfaceMessages.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.messages;

public class NetworkInterfaceMessages {
    private NetworkInterfaceMessages() {
    }


    //Tooltip message
    public static final String HELP_CHOOSE_NETWORK_INTERFACE = """
            Choose the network interface you want to use to communicate.
            Make sure that the network interface you are using communicates on the same LAN as the other computer you want to send files to.
            """;
}
