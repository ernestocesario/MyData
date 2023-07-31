/**
 * DeleteAccountService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.DeleteAccountTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service for delete the current account
public class DeleteAccountService extends Service<Boolean> {
    public final String usernameH;

    public DeleteAccountService(String usernameH) {
        this.usernameH = usernameH;
    }


    @Override
    protected Task<Boolean> createTask() {
        return new DeleteAccountTask(usernameH);
    }
}
