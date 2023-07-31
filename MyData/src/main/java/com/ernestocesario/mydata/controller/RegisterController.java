/**
 * RegisterController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.constants.ViewConstants;
import com.ernestocesario.mydata.model.services.RegistrationService;
import com.ernestocesario.mydata.utils.ResourceGetter;
import com.ernestocesario.mydata.utils.SecureRandomStringGenerator;
import com.ernestocesario.mydata.view.ViewSelector;
import com.ernestocesario.truepasswordfield.TruePasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.ernestocesario.mydata.model.constants.AccountConstants.*;
import static com.ernestocesario.mydata.model.messages.RegistrationMessages.*;

public class RegisterController {
    private RegistrationService registrationService;


    @FXML
    private Tooltip helpAutoGenPassTooltip;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label eyeIcon;

    @FXML
    private Label backIcon;

    @FXML
    private Label autoGenPassIcon;

    @FXML
    private TruePasswordField passwordField;

    @FXML
    private Button registerBtn;

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
        initRegistrationService();

        titleLabel.setFont(ViewSelector.getInstance().head_font);
        helpAutoGenPassTooltip.setText(HELP_AUTOGEN_PASSWORD);
        iconImageView.setImage(new Image(ResourceGetter.getAsStream(ViewConstants.ICON_PATH)));
        usernameField.setPromptText(USERNAME_FIELD_CREATION_PROMPT_TEXT);
        passwordField.setPromptText(PASSWORD_FIELD_CREATION_PROMPT_TEXT);
    }

    @FXML
    void onBackClick(MouseEvent event) {
        ViewSelector.getInstance().loadLoginView();
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
    void autoGeneratePassword(MouseEvent event) {
        passwordField.setText(SecureRandomStringGenerator.gen(PASSWORD_MIN_LEN, PASSWORD_MAX_LEN));
    }

    @FXML
    void onRegistrationClick(ActionEvent event) {
        doRegistration();
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            doRegistration();
    }

    //Private functions
    private void doRegistration() {
        AllComponentsEnabled(false);
        if (usernameField.getLength() < USERNAME_MIN_LEN || usernameField.getLength() > USERNAME_MAX_LEN ||
                passwordField.getLength() < PASSWORD_MIN_LEN || passwordField.getLength() > PASSWORD_MAX_LEN) {
            ViewSelector.getInstance().createAlert(REGISTRATION_FAILED_CONSTRAINTS, Alert.AlertType.ERROR).showAndWait();
            AllComponentsEnabled(true);
            return;
        }
        registrationService.setUserData(usernameField.getText(), passwordField.getText());
        registrationService.restart();
    }

    private void copyPasswordToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(passwordField.getText());
        clipboard.setContent(content);
    }

    private void AllComponentsEnabled(boolean val) {
        registerBtn.setDisable(!val);
        usernameField.setDisable(!val);
        passwordField.setDisable(!val);
        eyeIcon.setDisable(!val);
        backIcon.setDisable(!val);
        autoGenPassIcon.setDisable(!val);
        usernameLabel.setDisable(!val);
        passwordLabel.setDisable(!val);
    }

    private void initRegistrationService() {
        registrationService = new RegistrationService();

        registrationService.setOnSucceeded(workerStateEvent -> {
            if ((Boolean) workerStateEvent.getSource().getValue()) {
                copyPasswordToClipboard();
                ViewSelector.getInstance().createAlert(REGISTRATION_COMPLETED, Alert.AlertType.INFORMATION).showAndWait();
                ViewSelector.getInstance().loadLoginView();
            } else {
                ViewSelector.getInstance().createAlert(REGISTRATION_USER_ALREADY_EXISTS, Alert.AlertType.ERROR).showAndWait();
            }
            AllComponentsEnabled(true);
        });
    }
}
