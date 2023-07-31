/**
 * SettingsController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.Home;
import com.ernestocesario.mydata.model.Session;
import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.model.constants.ViewConstants;
import com.ernestocesario.mydata.model.services.ChangePasswordService;
import com.ernestocesario.mydata.model.services.DeleteAccountService;
import com.ernestocesario.mydata.model.services.GetAccountInfoService;
import com.ernestocesario.mydata.utils.Pair;
import com.ernestocesario.mydata.utils.ResourceGetter;
import com.ernestocesario.mydata.utils.SecureRandomStringGenerator;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import com.ernestocesario.mydata.utils.fileSpecs.FileSizeRepresentation;
import com.ernestocesario.mydata.utils.styles.Theme;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import com.ernestocesario.mydata.utils.utilityStageController.UtilyStageCloseHandler;
import com.ernestocesario.mydata.view.ViewSelector;
import com.ernestocesario.truepasswordfield.TruePasswordField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;

import static com.ernestocesario.mydata.model.constants.AccountConstants.PASSWORD_MAX_LEN;
import static com.ernestocesario.mydata.model.constants.AccountConstants.PASSWORD_MIN_LEN;
import static com.ernestocesario.mydata.model.messages.RegistrationMessages.HELP_AUTOGEN_PASSWORD;
import static com.ernestocesario.mydata.model.messages.RegistrationMessages.PASSWORD_FIELD_CREATION_PROMPT_TEXT;
import static com.ernestocesario.mydata.model.messages.SettingsMessages.*;

public class SettingsController implements UtilityStageController {
    //Local Constants
    private final int FONTSIZE_MAIN_LABEL = 32;
    private final int FONTSIZE_SIDE_LABEL = 17;
    private final int FONTSIZE_TITLE_LABEL = 29;
    private final int FONTSIZE_SUBSECTION_LABEL = 23;

    private GetAccountInfoService getAccountInfoService;
    private ChangePasswordService changePasswordService;
    private DeleteAccountService deleteAccountService;

    private UtilyStageCloseHandler closeHandler;


    @Override
    public void setCloseHandler(UtilyStageCloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }


    //tmp settings
    private String styleName = Settings.getInstance().getStyle().getName();
    private Theme theme = Settings.getInstance().getStyle().getTheme();
    private FileSizeRepresentation fileSizeRepresentation = Settings.getInstance().getFileSizeRepresentation();
    private int fileSizeRoundDecimalDigits = Settings.getInstance().getFileSizeRoundDecimalDigits();
    private boolean askBeforeRemoveFiles = Settings.getInstance().getAskBeforeRemoveFiles();
    private boolean askBeforeDeleteFiles = Settings.getInstance().getAskBeforeDeleteFiles();
    private boolean lockFilesOnClose = Settings.getInstance().getLockFilesOnClose();
    private boolean autoUnlockFilesToOpen = Settings.getInstance().getAutoUnlockFilesToOpen();
    private boolean autoUnlockFilesToSend = Settings.getInstance().getAutoUnlockFilesToSend();
    private boolean useDyslexicFont = Settings.getInstance().getUseDyslexicFont();


    @FXML
    private Tooltip helpAutoGenPassTooltip;

    @FXML
    private ScrollPane accountSettingsScrollPane;

    @FXML
    private ScrollPane appSettingsScrollPane;

    @FXML
    private AnchorPane aboutSubView;

    @FXML
    private TextArea aboutTextArea;

    @FXML
    private Label aboutUsSideLabel;

    @FXML
    private Label aboutUsTitleLabel;

    @FXML
    private Label accessibilitySideLabel;

    @FXML
    private AnchorPane accessibilitySubView;

    @FXML
    private Label accessibilityTitleLabel;

    @FXML
    private Label accountDetailsSubSectionLabel;

    @FXML
    private Label accountSettingsTitleLabel;

    @FXML
    private Label accountSideLabel;

    @FXML
    private AnchorPane accountSubView;

    @FXML
    private Label appSettingsSideLabel;

    @FXML
    private Label appSettingsTitleLabel;

    @FXML
    private AnchorPane appSubView;

    @FXML
    private ToggleSwitch askBeforeDeleteBtn;

    @FXML
    private ToggleSwitch askBeforeRemoveBtn;

    @FXML
    private Label autoGenPassIcon;

    @FXML
    private ToggleSwitch autoUnlockToOpenBtn;

    @FXML
    private ToggleSwitch autoUnlockToSendBtn;

    @FXML
    private Button changePasswordBtn;

    @FXML
    private Label changePasswordSubSectionLabel;

    @FXML
    private Button deleteAccountBtn;

    @FXML
    private Label deleteAccountSubSectionLabel;

    @FXML
    private AnchorPane displayAnchorPane;

    @FXML
    private Label dyslexicFontSubSectionLabel;

    @FXML
    private Label dyslexicLabel;

    @FXML
    private Label eyeIcon;

    @FXML
    private Label filesSubSectionLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private ToggleSwitch lockOnCloseBtn;

    @FXML
    private TruePasswordField newPasswordField;

    @FXML
    private TextField numAssociatedFilesField;

    @FXML
    private TextField numProtectedFilesField;

    @FXML
    private Label protectionSubSectionLabel;

    @FXML
    private BorderPane root;

    @FXML
    private Button saveBtn;

    @FXML
    private SplitPane settingsSplitPane;

    @FXML
    private ChoiceBox<FileSizeRepresentation> sizeFileBox;

    @FXML
    private ChoiceBox<Integer> sizeRoundBox;

    @FXML
    private ChoiceBox<String> styleBox;

    @FXML
    private Label stylesSubSectionLabel;

    @FXML
    private ChoiceBox<Theme> themeBox;

    @FXML
    private Label mainLabel;

    @FXML
    private Label uiCustomizationSideLabel;

    @FXML
    private AnchorPane uiCustomizationSubView;

    @FXML
    private Label uiCustomizationTitleLabel;

    @FXML
    private ToggleSwitch useDyslexicFontBtn;

    @FXML
    private TextField usernameField;


    @FXML
    void initialize() {
        mainLabel.setFont(Font.font(ViewSelector.getInstance().head_font.getFamily(), FONTSIZE_MAIN_LABEL));

        initLabelSideSection();
        initLabelTitleSection();
        initLabelSubSection();
        iconImageView.setImage(new Image(ResourceGetter.getAsStream(ViewConstants.ICON_PATH)));
        initDividerListener();
        initAccountSection();
        initUICustomizationSection();
        initAppSettingsSection();
        initAccessibilitySection();
        initAboutUsSection();
    }

    @FXML
    void onAccountClick(MouseEvent event) {
        hideAllSections();
        accountSubView.setVisible(true);
    }

    @FXML
    void onUiCustomizationClick(MouseEvent event) {
        hideAllSections();
        uiCustomizationSubView.setVisible(true);
    }

    @FXML
    void onAppSettingsClick(MouseEvent event) {
        hideAllSections();
        appSubView.setVisible(true);
    }

    @FXML
    void onAccessibilityClick(MouseEvent event) {
        hideAllSections();
        accessibilitySubView.setVisible(true);
    }

    @FXML
    void onAboutUsClick(MouseEvent event) {
        hideAllSections();
        aboutSubView.setVisible(true);
    }

    @FXML
    void onEyeClicked(MouseEvent event) {
        if (newPasswordField.getPasswordVisible()) {
            newPasswordField.setPasswordVisible(false);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye");
        } else {
            newPasswordField.setPasswordVisible(true);
            ((FontIcon) eyeIcon.getGraphic()).setIconLiteral("fas-eye-slash");
        }
    }

    @FXML
    void autoGeneratePassword(MouseEvent event) {
        newPasswordField.setText(SecureRandomStringGenerator.gen(PASSWORD_MIN_LEN, PASSWORD_MAX_LEN));
    }

    @FXML
    void onChangePasswordAction(ActionEvent event) {
        root.setDisable(true);
        if (newPasswordField.getLength() < PASSWORD_MIN_LEN || newPasswordField.getLength() > PASSWORD_MAX_LEN) {
            ViewSelector.getInstance().createAlert(PASSWORD_FAILED_CONSTRAINTS, Alert.AlertType.ERROR).showAndWait();
            root.setDisable(false);
        } else {
            changePasswordService.setNewPassword(newPasswordField.getText());
            ViewSelector.getInstance().createAlert(CHANGE_PASSWORD_FILES_UNLOCKED, Alert.AlertType.INFORMATION).showAndWait();
            if (!Home.getInstance().applyFileProtectionToAll(FileProtection.Unlocked, event1 -> {
                changePasswordService.restart();
            }))
                ViewSelector.getInstance().createAlert(IMPOSSIBLE_CHANGE_PASSWORD_ONGOING_OPERATIONS, Alert.AlertType.ERROR).showAndWait();
        }
    }

    @FXML
    void onDeleteAccountAction(ActionEvent event) {
        Alert alert = ViewSelector.getInstance().createAlert(ASK_DELETE_ACCOUNT, Alert.AlertType.CONFIRMATION, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.NO)
            return;

        root.setDisable(true);
        deleteAccountService.restart();
    }

    @FXML
    void onSaveAction(ActionEvent event) {
        root.setDisable(true);
        ViewSelector.getInstance().createAlert(APP_CLOSE_APPLY_SETTINGS, Alert.AlertType.INFORMATION).showAndWait();

        Settings.getInstance().setStyleName(styleName);
        Settings.getInstance().setStyleTheme(theme);
        Settings.getInstance().setFileSizeRepresentation(fileSizeRepresentation);
        Settings.getInstance().setFileSizeRoundDecimalDigits(fileSizeRoundDecimalDigits);
        Settings.getInstance().setAskBeforeRemoveFiles(askBeforeRemoveFiles);
        Settings.getInstance().setAskBeforeDeleteFiles(askBeforeDeleteFiles);
        Settings.getInstance().setLockFilesOnClose(lockFilesOnClose);
        Settings.getInstance().setAutoUnlockFilesToOpen(autoUnlockFilesToOpen);
        Settings.getInstance().setAutoUnlockFilesToSend(autoUnlockFilesToSend);
        Settings.getInstance().setUseDyslexicFont(useDyslexicFont);

        Settings.getInstance().saveSettings();
        ViewSelector.getInstance().doExitRequest();
        closeHandler.handleStageClose(true);
    }


    //Private Functions
    private void initLabelSideSection() {
        accountSideLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SIDE_LABEL));
        uiCustomizationSideLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SIDE_LABEL));
        appSettingsSideLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SIDE_LABEL));
        accessibilitySideLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SIDE_LABEL));
        aboutUsSideLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SIDE_LABEL));
    }

    private void initLabelTitleSection() {
        accountSettingsTitleLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TITLE_LABEL));
        uiCustomizationTitleLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TITLE_LABEL));
        appSettingsTitleLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TITLE_LABEL));
        accessibilityTitleLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TITLE_LABEL));
        aboutUsTitleLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TITLE_LABEL));
    }

    private void initLabelSubSection() {
        accountDetailsSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        changePasswordSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        deleteAccountSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        stylesSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        filesSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        protectionSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
        dyslexicFontSubSectionLabel.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_SUBSECTION_LABEL));
    }

    private void initDividerListener() {
        SplitPane.Divider divider = settingsSplitPane.getDividers().get(0);
        divider.positionProperty().addListener((observable, oldValue, newValue) -> divider.setPosition(0.3));
    }

    private void initAccountSection() {
        initAccountInfoService();
        initChangePasswordService();
        initDeleteAccountService();

        helpAutoGenPassTooltip.setText(HELP_AUTOGEN_PASSWORD);
        newPasswordField.setPromptText(PASSWORD_FIELD_CREATION_PROMPT_TEXT);
        usernameField.setText(Session.getInstance().getUsername());
    }

    private void initUICustomizationSection() {
        //Getter
        styleBox.setItems(FXCollections.observableArrayList(ViewConstants.CSS_LIST));
        styleBox.getSelectionModel().select(Settings.getInstance().getStyle().getName());

        themeBox.getItems().addAll(Theme.Light, Theme.Dark);
        themeBox.getSelectionModel().select(Settings.getInstance().getStyle().getTheme());


        //Setter
        styleBox.valueProperty().addListener((observable, oldValue, newValue) -> styleName = newValue);
        themeBox.valueProperty().addListener((observable, oldValue, newValue) -> theme = newValue);
    }

    private void initAppSettingsSection() {
        //Getter
        sizeFileBox.getItems().addAll(FileSizeRepresentation.Byte,
                FileSizeRepresentation.KiloByte,
                FileSizeRepresentation.MegaByte,
                FileSizeRepresentation.GigaByte,
                FileSizeRepresentation.TeraByte,
                FileSizeRepresentation.PetaByte);

        sizeFileBox.getSelectionModel().select(Settings.getInstance().getFileSizeRepresentation());

        sizeRoundBox.getItems().addAll(1, 2, 3);
        sizeRoundBox.getSelectionModel().select((Integer) Settings.getInstance().getFileSizeRoundDecimalDigits());

        askBeforeRemoveBtn.setSelected(Settings.getInstance().getAskBeforeRemoveFiles());
        askBeforeDeleteBtn.setSelected(Settings.getInstance().getAskBeforeDeleteFiles());
        lockOnCloseBtn.setSelected(Settings.getInstance().getLockFilesOnClose());
        autoUnlockToOpenBtn.setSelected(Settings.getInstance().getAutoUnlockFilesToOpen());
        autoUnlockToSendBtn.setSelected(Settings.getInstance().getAutoUnlockFilesToSend());


        //Setter
        sizeFileBox.valueProperty().addListener((observable, oldValue, newValue) -> fileSizeRepresentation = newValue);
        sizeRoundBox.valueProperty().addListener((observable, oldValue, newValue) -> fileSizeRoundDecimalDigits = newValue);
        askBeforeRemoveBtn.selectedProperty().addListener((observable, oldValue, newValue) -> askBeforeRemoveFiles = newValue);
        askBeforeDeleteBtn.selectedProperty().addListener((observable, oldValue, newValue) -> askBeforeDeleteFiles = newValue);
        lockOnCloseBtn.selectedProperty().addListener((observable, oldValue, newValue) -> lockFilesOnClose = newValue);
        autoUnlockToOpenBtn.selectedProperty().addListener((observable, oldValue, newValue) -> autoUnlockFilesToOpen = newValue);
        autoUnlockToSendBtn.selectedProperty().addListener((observable, oldValue, newValue) -> autoUnlockFilesToSend = newValue);
    }

    private void initAccessibilitySection() {
        dyslexicLabel.setFont(Font.font(ViewSelector.getInstance().dyslexic_font.getFamily()));

        //Getter
        useDyslexicFontBtn.setSelected(Settings.getInstance().getUseDyslexicFont());


        //Setter
        useDyslexicFontBtn.selectedProperty().addListener((observable, oldValue, newValue) -> useDyslexicFont = newValue);
    }

    private void initAboutUsSection() {
        //Getter
        aboutTextArea.setText(ABOUT_US_TEXT);
    }

    private void hideAllSections() {
        accountSubView.setVisible(false);
        uiCustomizationSubView.setVisible(false);
        appSubView.setVisible(false);
        accessibilitySubView.setVisible(false);
        aboutSubView.setVisible(false);
    }

    private void initAccountInfoService() {
        getAccountInfoService = new GetAccountInfoService(Session.getInstance().getUsernameH());

        getAccountInfoService.setOnSucceeded(event -> {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer> accountInfo = (Pair<Integer, Integer>) event.getSource().getValue();

            numAssociatedFilesField.setText(accountInfo.first.toString());
            numProtectedFilesField.setText(accountInfo.second.toString());
        });

        getAccountInfoService.restart();
    }

    private void initChangePasswordService() {
        changePasswordService = new ChangePasswordService(Session.getInstance().getUsernameH());

        changePasswordService.setOnSucceeded(event -> {
            copyPasswordToClipboard();
            ViewSelector.getInstance().createAlert(CHANGE_PASSWORD_SUCCEED, Alert.AlertType.INFORMATION).showAndWait();
            ViewSelector.getInstance().exitNow();
            closeHandler.handleStageClose(true);
        });
    }

    private void initDeleteAccountService() {
        deleteAccountService = new DeleteAccountService(Session.getInstance().getUsernameH());

        deleteAccountService.setOnSucceeded(event -> {
            boolean accountDeleted = (Boolean) event.getSource().getValue();

            if (accountDeleted) {
                ViewSelector.getInstance().createAlert(DELETE_ACCOUNT_SUCCEED, Alert.AlertType.INFORMATION).showAndWait();
                ViewSelector.getInstance().exitNow();
                closeHandler.handleStageClose(true);
            } else {
                ViewSelector.getInstance().createAlert(DELETE_ACCOUNT_FAILED_CONTAINS_FILES, Alert.AlertType.ERROR).showAndWait();
                root.setDisable(false);
            }
        });
    }

    private void copyPasswordToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(newPasswordField.getText());
        clipboard.setContent(content);
    }
}
