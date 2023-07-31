/**
 * SenderCodeController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.SenderCode;
import com.ernestocesario.mydata.model.services.SenderFilesService;
import com.ernestocesario.mydata.utils.InetSocketAddressEncoder;
import com.ernestocesario.mydata.utils.OperationResult;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import com.ernestocesario.mydata.utils.utilityStageController.UtilyStageCloseHandler;
import com.ernestocesario.truepasswordfield.TruePasswordField;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.InetSocketAddress;

public class SenderCodeController implements UtilityStageController {
    private UtilyStageCloseHandler closeHandler;
    private SenderFilesService senderFilesService;
    private InetSocketAddress inetSocketAddress;
    private SimpleBooleanProperty abortButtonVisibilityProperty;


    @Override
    public void setCloseHandler(UtilyStageCloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }


    private final ChangeListener<OperationResult<Integer>> senderServiceChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends OperationResult<Integer>> observable, OperationResult<Integer> oldValue, OperationResult<Integer> newValue) {
            if (closeHandler != null) {
                closeHandler.handleStageClose(true);
                senderFilesService.valueProperty().removeListener(this);
            }
        }
    };


    @FXML
    private Label backIcon;

    @FXML
    private TruePasswordField codeField;

    @FXML
    private Label eyeIcon;

    @FXML
    public void initialize() {
        senderFilesService = SenderCode.getInstance().getSenderFilesService();
        inetSocketAddress = SenderCode.getInstance().getInetSocketAddress();
        abortButtonVisibilityProperty = SenderCode.getInstance().getAbortButtonVisibilityProperty();
        senderFilesService.valueProperty().addListener(senderServiceChangeListener);

        codeField.setText(InetSocketAddressEncoder.encode(inetSocketAddress));
        senderFilesService.restart();
        abortButtonVisibilityProperty.set(true);
    }

    @FXML
    void onBackClick(MouseEvent event) {
        closeHandler.handleStageClose(null);
    }

    @FXML
    void onEyeClick(MouseEvent event) {
        if (codeField.getPasswordVisible()) {
            codeField.setPasswordVisible(false);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye");
        } else {
            codeField.setPasswordVisible(true);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye-slash");
        }
    }
}
