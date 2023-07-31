/**
 * LoginTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Session;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashAlgorithms;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashCalc;
import javafx.concurrent.Task;

import java.util.Objects;

public class LoginTask extends Task<Boolean> {
    private final String username, password;

    public LoginTask(String username, String password) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    @Override
    protected Boolean call() throws Exception {
        String usernameH = HashCalc.getHash(HashAlgorithms.BLAKE2S, username);

        var getUserPasswordTask = new GetUserPasswordTask(usernameH);
        getUserPasswordTask.run();

        String passwordHFromDB = getUserPasswordTask.get();

        if (passwordHFromDB == null)  //The user don't exists
            return false;

        boolean validLogin = HashCalc.checkHash(HashAlgorithms.BCRYPT, password, passwordHFromDB);

        if (validLogin)
            Session.getInstance().create(username, usernameH, password);

        return validLogin;
    }
}
