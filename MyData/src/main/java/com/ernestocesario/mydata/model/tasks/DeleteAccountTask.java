/**
 * DeleteAccountTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import com.ernestocesario.mydata.model.Database;
import javafx.concurrent.Task;

import java.util.Objects;

public class DeleteAccountTask extends Task<Boolean> {
    private final String usernameH;

    public DeleteAccountTask(String usernameH) {
        this.usernameH = Objects.requireNonNull(usernameH);
    }

    @Override
    protected Boolean call() throws Exception {
        int numFileAssociated = Database.getInstance().getAccountInfoByUser(usernameH).first;

        if (numFileAssociated > 0)
            return false;

        Database.getInstance().removeUser(usernameH);
        return true;
    }
}
