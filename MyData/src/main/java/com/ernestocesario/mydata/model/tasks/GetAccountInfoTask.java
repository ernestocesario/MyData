/**
 * GetAccountInfoTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import com.ernestocesario.mydata.utils.Pair;
import javafx.concurrent.Task;

import java.util.Objects;

public class GetAccountInfoTask extends Task<Pair<Integer, Integer>> {
    private final String usernameH;

    public GetAccountInfoTask(String usernameH) {
        this.usernameH = Objects.requireNonNull(usernameH);
    }

    @Override
    protected Pair<Integer, Integer> call() {
        return Database.getInstance().getAccountInfoByUser(usernameH);
    }
}
