/**
 * RegistrationService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.RegistrationTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to register a new account
public class RegistrationService extends Service<Boolean> {
    private String username, password;

    public RegistrationService() {
    }

    public void setUserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new RegistrationTask(username, password);
    }
}
