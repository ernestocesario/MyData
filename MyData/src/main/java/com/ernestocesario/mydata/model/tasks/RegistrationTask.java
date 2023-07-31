/**
 * RegistrationTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashAlgorithms;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashCalc;
import javafx.concurrent.Task;

import java.util.Objects;

public class RegistrationTask extends Task<Boolean> {
    private final String username, password;

    public RegistrationTask(String username, String password) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
    }

    @Override
    protected Boolean call() throws Exception {
        String usernameH = HashCalc.getHash(HashAlgorithms.BLAKE2S, username);

        var getUserPasswordTask = new GetUserPasswordTask(usernameH);

        if (getUserPasswordTask.call() != null)  //The user already exists
            return false;

        String passwordH = HashCalc.getHash(HashAlgorithms.BCRYPT, password);

        Database.getInstance().addUser(usernameH, passwordH);
        return true;
    }
}
