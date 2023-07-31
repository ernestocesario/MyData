/**
 * GetUserPasswordTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import javafx.concurrent.Task;

import java.util.Objects;

public class GetUserPasswordTask extends Task<String> {
    private final String usernameH;

    public GetUserPasswordTask(String usernameH) {
        this.usernameH = Objects.requireNonNull(usernameH);
    }

    @Override
    protected String call() {
        return Database.getInstance().getPasswordByUser(usernameH);
    }
}
