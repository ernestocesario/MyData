/**
 * ChangePasswordTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.model.Session;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashAlgorithms;
import com.ernestocesario.mydata.utils.hashingAlgorithms.HashCalc;
import javafx.concurrent.Task;

import java.util.Objects;

public class ChangePasswordTask extends Task<Void> {
    private final String usernameH;
    private final String newPassword;


    public ChangePasswordTask(String usernameH, String newPassword) {
        this.usernameH = Objects.requireNonNull(usernameH);
        this.newPassword = Objects.requireNonNull(newPassword);
    }

    @Override
    protected Void call() throws Exception {
        final String passwordH = HashCalc.getHash(HashAlgorithms.BCRYPT, newPassword);
        Database.getInstance().updateUserPassword(usernameH, passwordH);
        Session.getInstance().setPassword(newPassword);
        return null;
    }
}
