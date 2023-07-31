/**
 * StageTransitions.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StageTransitions {
    private StageTransitions() {
    } //no instantiation


    public static void fadeIn(Stage stage, int duration, EventHandler<ActionEvent> event) throws NullPointerException {  //makes a fade-in effect to the stage
        fadeTransition(stage, duration, 0.0, 1.0, event);
    }

    public static void fadeOut(Stage stage, int duration, EventHandler<ActionEvent> event) throws NullPointerException {  //makes a fade-out effect to the stage
        fadeTransition(stage, duration, 1.0, 0.0, event);
    }


    //Private methods

    //makes the fade (in or out) transition
    private static void fadeTransition(Stage stage, int duration, double fromValue, double toValue, EventHandler<ActionEvent> eventHandler) throws NullPointerException {
        if (stage == null)
            throw new NullPointerException("Stage cannot be null!");
        else if (stage.getScene() == null)
            throw new NullPointerException("There is no scene associated with the stage!");
        else if (stage.getScene().getRoot() == null)
            throw new NullPointerException("The scene associated with the stage has no root node!");

        Node rootNode = stage.getScene().getRoot();
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), rootNode);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setOnFinished(eventHandler);  //event to do when the transition ends (can be null)
        fadeTransition.play();
    }
}
