/**
 * InetSocketAddressEncoder.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Base64;


//Utility class used to convert an InetSocketAddress (IPv4 or IPv6) with port, to a shorter hexadecimal string, or vice versa.
public class InetSocketAddressEncoder {
    //Local Constants
    private static final int PORT_BYTESIZE = 2;


    private InetSocketAddressEncoder() {
    }  //No instantiation

    public static String encode(InetSocketAddress inetSocketAddress) {
        String ip = inetSocketAddress.getHostString();
        int port = inetSocketAddress.getPort();

        InetAddress address;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            return null;
        }

        byte[] ipBytes = address.getAddress();
        ByteBuffer buffer = ByteBuffer.allocate(ipBytes.length + PORT_BYTESIZE);
        buffer.put(ipBytes);
        buffer.putShort((short) port);

        byte[] combinedBytes = buffer.array();

        return Base64.getEncoder().encodeToString(combinedBytes);
    }

    public static InetSocketAddress decode(String compressed) {
        InetAddress address;
        int port;

        try {
            byte[] combinedBytes = Base64.getDecoder().decode(compressed);

            ByteBuffer buffer = ByteBuffer.wrap(combinedBytes);
            byte[] ipBytes = new byte[combinedBytes.length - PORT_BYTESIZE];
            buffer.get(ipBytes);

            port = buffer.getShort() & 0xffff;

            address = InetAddress.getByAddress(ipBytes);
        } catch (Exception e) {
            return null;
        }

        return new InetSocketAddress(address, port);
    }
}
