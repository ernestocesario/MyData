/**
 * SenderCode.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model;

import com.ernestocesario.mydata.model.services.SenderFilesService;
import javafx.beans.property.SimpleBooleanProperty;

import java.net.InetSocketAddress;

//SenderCode Model
public class SenderCode {
    private static final SenderCode instance = new SenderCode();

    private SenderFilesService senderFilesService;
    private InetSocketAddress inetSocketAddress;
    private SimpleBooleanProperty abortButtonVisibility;

    private SenderCode() {
    }

    public static SenderCode getInstance() {
        return instance;
    }


    public SenderFilesService getSenderFilesService() {
        return senderFilesService;
    }

    public void setSenderFilesService(SenderFilesService senderFilesService) {
        this.senderFilesService = senderFilesService;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void setInetSocketAddress(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    public SimpleBooleanProperty getAbortButtonVisibilityProperty() {
        return abortButtonVisibility;
    }

    public void setAbortButtonVisibilityProperty(SimpleBooleanProperty abortButtonVisibility) {
        this.abortButtonVisibility = abortButtonVisibility;
    }
}
