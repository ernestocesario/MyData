/**
 * ChangePasswordService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.ChangePasswordTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to change the password of the current account
public class ChangePasswordService extends Service<Void> {
    private final String usernameH;
    private String newPassword;


    public ChangePasswordService(String usernameH) {
        this.usernameH = usernameH;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    protected Task<Void> createTask() {
        return new ChangePasswordTask(usernameH, newPassword);
    }
}
