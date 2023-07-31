/**
 * UtilityStage.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;

import com.ernestocesario.mydata.model.constants.ViewConstants;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import com.ernestocesario.mydata.utils.utilityStageController.UtilyStageCloseHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class UtilityStage<T> {
    private final Window owner;
    private Stage stage = null;
    private UtilityStageController controller;
    private T returnData = null;


    public UtilityStage(Window owner) {
        this.owner = owner;
    }


    //Initialize a utilityStage from a stage and its controller
    public void init(Stage s, UtilityStageController c) {
        if (this.stage != null)
            return;
        stage = s;
        controller = c;

        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.setResizable(false);

        controller.setCloseHandler(new UtilyStageCloseHandler() {
            @Override
            @SuppressWarnings("unchecked")
            public <K> void handleStageClose(K result) {
                returnData = (T) result;
                stage.close();
                if (returnData != null && result != null && !returnData.getClass().equals(result.getClass()))
                    throw new IllegalArgumentException();
            }
        });

        stage.setOnCloseRequest(windowEvent -> returnData = null);
    }

    //Show and wait for the stage, then return the result (can be null)
    public T getResult() {
        StageTransitions.fadeIn(stage, ViewConstants.FADEIN_MILLISECONDS, null);
        stage.showAndWait();
        return returnData;
    }
}
