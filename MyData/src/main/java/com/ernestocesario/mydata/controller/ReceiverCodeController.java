/**
 * ReceiverCodeController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.messages.HomeMessages;
import com.ernestocesario.mydata.model.services.TransferCodeDecoderService;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import com.ernestocesario.mydata.utils.utilityStageController.UtilyStageCloseHandler;
import com.ernestocesario.mydata.view.ViewSelector;
import com.ernestocesario.truepasswordfield.TruePasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.InetSocketAddress;

public class ReceiverCodeController implements UtilityStageController {
    private UtilyStageCloseHandler closeHandler;
    private TransferCodeDecoderService transferCodeDecoderService;


    @Override
    public void setCloseHandler(UtilyStageCloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }


    @FXML
    private Label backIcon;

    @FXML
    private TruePasswordField codeField;

    @FXML
    private Label eyeIcon;

    @FXML
    private Button receiveBtn;


    @FXML
    void initialize() {
        transferCodeDecoderService = new TransferCodeDecoderService();

        transferCodeDecoderService.setOnSucceeded(event -> {
            InetSocketAddress socketAddress = (InetSocketAddress) event.getSource().getValue();

            if (socketAddress != null)
                closeHandler.handleStageClose(socketAddress);
            else {
                ViewSelector.getInstance().createAlert(HomeMessages.TRANSFER_CODE_INVALID, Alert.AlertType.ERROR).showAndWait();
                receiveBtn.setDisable(false);
            }
        });
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

    @FXML
    void onReceiveAction(ActionEvent event) {
        receiveBtn.setDisable(true);
        transferCodeDecoderService.setTransferCode(codeField.getText());

        transferCodeDecoderService.restart();
    }
}
