/**
 * UtilityStageController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.utilityStageController;


//Interface implemented by UtilityStage controllers to define a UtilityStageCloseHandler for the current stage
public interface UtilityStageController {
    default void setCloseHandler(UtilyStageCloseHandler closeHandler) {
    }
}
