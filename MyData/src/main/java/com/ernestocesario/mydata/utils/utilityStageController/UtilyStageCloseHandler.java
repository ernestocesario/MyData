/**
 * UtilyStageCloseHandler.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.utilityStageController;


//Interface used by the UtilityStage class to close a stage from its controller and return a result.
public interface UtilyStageCloseHandler {
    <T> void handleStageClose(T result);
}
