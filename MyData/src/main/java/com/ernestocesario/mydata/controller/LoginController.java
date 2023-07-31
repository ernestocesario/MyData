/**
 * LoginController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.constants.ViewConstants;
import com.ernestocesario.mydata.model.messages.LoginMessages;
import com.ernestocesario.mydata.model.services.LoginService;
import com.ernestocesario.mydata.utils.ResourceGetter;
import com.ernestocesario.mydata.view.ViewSelector;
import com.ernestocesario.truepasswordfield.TruePasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.ernestocesario.mydata.model.constants.AccountConstants.*;

public class LoginController {
    private LoginService loginService;


    @FXML
    private ImageView iconImageView;

    @FXML
    private Label eyeIcon;

    @FXML
    private Button loginBtn;

    @FXML
    private TruePasswordField passwordField;

    @FXML
    private Label registerLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField usernameField;


    @FXML
    void initialize() {
        initLoginService();

        titleLabel.setFont(ViewSelector.getInstance().head_font);
        iconImageView.setImage(new Image(ResourceGetter.getAsStream(ViewConstants.ICON_PATH)));
    }

    @FXML
    void onEyeClicked(MouseEvent event) {
        if (passwordField.getPasswordVisible()) {
            passwordField.setPasswordVisible(false);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye");
        } else {
            passwordField.setPasswordVisible(true);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye-slash");
        }
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        doLogin();
    }

    @FXML
    void onRegisterClick(MouseEvent event) {
        ViewSelector.getInstance().loadRegisterView();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            doLogin();
    }

    //Private Functions
    private void doLogin() {
        AllComponentsEnabled(false);
        if (usernameField.getLength() < USERNAME_MIN_LEN || usernameField.getLength() > USERNAME_MAX_LEN ||
                passwordField.getLength() < PASSWORD_MIN_LEN || passwordField.getLength() > PASSWORD_MAX_LEN) {
            AllComponentsEnabled(true);
            ViewSelector.getInstance().createAlert(LoginMessages.LOGIN_FAILED, Alert.AlertType.ERROR).showAndWait();
            return;
        }

        loginService.setUserData(usernameField.getText(), passwordField.getText());
        loginService.restart();
    }

    private void AllComponentsEnabled(boolean val) {
        loginBtn.setDisable(!val);
        registerLabel.setDisable(!val);
        usernameField.setDisable(!val);
        passwordField.setDisable(!val);
        eyeIcon.setDisable(!val);
        usernameLabel.setDisable(!val);
        passwordLabel.setDisable(!val);
    }

    private void initLoginService() {
        loginService = new LoginService();

        loginService.setOnSucceeded(workerStateEvent -> {
            if ((boolean) workerStateEvent.getSource().getValue()) {
                ViewSelector.getInstance().loadMainView();
            } else
                ViewSelector.getInstance().createAlert(LoginMessages.LOGIN_FAILED, Alert.AlertType.ERROR).showAndWait();
            AllComponentsEnabled(true);
        });
    }
}
