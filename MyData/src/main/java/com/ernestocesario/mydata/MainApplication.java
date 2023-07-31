/**
 * MainApplication.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata;

import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.utils.SingleInstanceChecker;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) {
        Settings.initialize();
        SingleInstanceChecker.initialize();
        ViewSelector.getInstance().initialize(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}