/**
 * LoginService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.LoginTask;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to check login data
public class LoginService extends Service<Boolean> {
    private String username, password;

    public LoginService() {
    }

    public void setUserData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new LoginTask(username, password);
    }
}