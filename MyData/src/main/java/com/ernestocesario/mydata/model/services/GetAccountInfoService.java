/**
 * GetAccountInfoService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.GetAccountInfoTask;
import com.ernestocesario.mydata.utils.Pair;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

//Service to get information of the current account
public class GetAccountInfoService extends Service<Pair<Integer, Integer>> {
    private final String usernameH;

    public GetAccountInfoService(String usernameH) {
        this.usernameH = usernameH;
    }

    @Override
    protected Task<Pair<Integer, Integer>> createTask() {
        return new GetAccountInfoTask(usernameH);
    }
}
