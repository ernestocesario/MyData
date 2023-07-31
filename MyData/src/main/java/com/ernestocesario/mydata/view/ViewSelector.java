/**
 * ViewSelector.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.view;

import com.ernestocesario.mydata.model.Home;
import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.model.constants.AppConstants;
import com.ernestocesario.mydata.model.constants.ViewConstants;
import com.ernestocesario.mydata.model.messages.GeneralMessages;
import com.ernestocesario.mydata.model.messages.HomeMessages;
import com.ernestocesario.mydata.utils.StageTransitions;
import com.ernestocesario.mydata.utils.UtilityStage;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Objects;

public class ViewSelector {
    //Local Constants
    private final String LOADED_FXML_STAGE_PROPERTY = "loadedFXML";


    private static final ViewSelector instance = new ViewSelector();
    private final SimpleBooleanProperty exitGrantedProperty = new SimpleBooleanProperty(false);
    private final Image iconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ViewConstants.ICON_BLACK_PATH)));
    private Stage stage;
    public Font head_font, general_font, dyslexic_font;


    private ViewSelector() {
    }


    public static ViewSelector getInstance() {
        return instance;
    }

    public void initialize(Stage s) {
        stage = s;

        //Load the fonts
        head_font = Font.loadFont(Objects.requireNonNull(getClass().getResource(ViewConstants.HEAD_FONT)).toExternalForm(), 72);

        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            dyslexic_font = Font.loadFont(Objects.requireNonNull(getClass().getResource(ViewConstants.DYSLEXIC_FONT_MACOS)).toExternalForm(), 14);
        else
            dyslexic_font = Font.loadFont(Objects.requireNonNull(getClass().getResource(ViewConstants.DYSLEXIC_FONT_NOT_MACOS)).toExternalForm(), 14);


        if (Settings.getInstance().getUseDyslexicFont())
            general_font = dyslexic_font;
        else
            general_font = Font.loadFont(Objects.requireNonNull(getClass().getResource(ViewConstants.GENERAL_FONT)).toExternalForm(), 14);


        //Name the stage
        stage.setTitle(AppConstants.APP_NAME);

        //Load the icon
        setStageIcon(stage);

        //Set stage closing rules
        exitGrantedProperty.addListener((observable, oldValue, newValue) -> StageTransitions.fadeOut(stage, ViewConstants.FADEOUT_MILLISECONDS, event -> System.exit(0)));
        stage.setOnCloseRequest(this::stageClosingRules);

        loadLoginView();
        centerOnScreen(stage);
    }


    //Function Scene loaders
    public void loadLoginView() {
        loadFXML("login-view.fxml", stage);
        stage.setResizable(false);
        StageTransitions.fadeIn(stage, ViewConstants.FADEIN_MILLISECONDS, null);
        stage.show();
        stage.toFront();
    }

    public void loadRegisterView() {
        loadFXML("register-view.fxml", stage);
        stage.setResizable(false);
        StageTransitions.fadeIn(stage, ViewConstants.FADEIN_MILLISECONDS, null);
        stage.show();
        stage.toFront();
    }

    public void loadMainView() {
        loadFXML("home-view.fxml", stage);
        stage.setResizable(true);
        StageTransitions.fadeIn(stage, ViewConstants.FADEIN_MILLISECONDS, null);
        stage.show();
        stage.toFront();
    }

    public UtilityStage<InetSocketAddress> getNetworkAddressView() {
        return createUtilityStage("networkInterfaceChoicer-view.fxml", stage);
    }

    public UtilityStage<Boolean> getSenderCodeView() {
        return createUtilityStage("senderCode-view.fxml", stage);
    }

    public UtilityStage<InetSocketAddress> getReceiverCodeView() {
        return createUtilityStage("receiverCode-view.fxml", stage);
    }


    //Method to create utilityStage by passing the FXML to load and the owner of the utilityStage
    public UtilityStage<Boolean> loadSettingsView() {
        return createUtilityStage("settings-view.fxml", stage);
    }

    //Asks the home model if the app can be closed safely (i.e., without interrupting important operations), and if it can it closes it
    public void doExitRequest() {
        String loadedFXML = getLoadedFXML(stage);
        if (loadedFXML.equals(""))
            return;

        if (loadedFXML.equals("home-view.fxml") && Home.getInstance() != null) {
            if (!Home.getInstance().doExitRequest())
                ViewSelector.getInstance().createAlert(HomeMessages.IMPOSSIBLE_EXIT_ONGOING_OPERATIONS, Alert.AlertType.WARNING).showAndWait();
        } else
            exitGrantedProperty.set(true);
    }

    //Close the app without control anything
    public void exitNow() {
        exitGrantedProperty.set(true);
    }


    //Create an alert with the passed arguments
    public Alert createAlert(String text, Alert.AlertType alertType, ButtonType... buttonTypes) {
        Alert alert = new Alert(alertType, text, buttonTypes);
        alert.setTitle(AppConstants.APP_NAME);
        alert.setHeaderText(alertType.toString().charAt(0) + alertType.toString().substring(1).toLowerCase());
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(iconImage);
        setStyleForAlert(alert);
        return alert;
    }

    //Open a file chooser with open multiple option and returns the selection
    public List<File> openFilesChooser() {
        return new FileChooser().showOpenMultipleDialog(stage);
    }

    //Oper a directory chooser and returns the selection
    public File saveDirectoryChooser() {
        return new DirectoryChooser().showDialog(stage);
    }


    //Private Functions

    //Creates a utilityStage and returns it
    private <T> UtilityStage<T> createUtilityStage(String fxmlFilename, Window owner) {
        UtilityStage<T> utilityStage = new UtilityStage<>(owner);

        Stage s = new Stage();
        Object controller = loadFXML(fxmlFilename, s);

        utilityStage.init(s, (UtilityStageController) controller);
        return utilityStage;
    }

    //Loads the passed fxml in the passed stage
    private Object loadFXML(String fxmlFilename, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewConstants.FXML_PATH + fxmlFilename));
            Scene scene = new Scene(loader.load());
            setStyleForScene(scene);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.getProperties().put(LOADED_FXML_STAGE_PROPERTY, fxmlFilename);
            return loader.getController();
        } catch (IOException e) {
            ViewSelector.getInstance().createAlert(GeneralMessages.FATAL_ERR, Alert.AlertType.ERROR).showAndWait();
            System.exit(1);
        }
        return null;
    }

    private void setStageIcon(Stage s) {
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ViewConstants.ICON_BLACK_PATH)));
        s.getIcons().add(icon);
    }

    private void centerOnScreen(Stage s) { // Warning: call this method only after the stage was made visible
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - s.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - s.getHeight()) / 2;

        s.setX(centerX);
        s.setY(centerY);
    }

    //Sets styles and font for the passed scene
    private void setStyleForScene(Scene scene) throws NullPointerException {
        if (scene == null)
            throw new NullPointerException("Scene cannot be null");

        try {
            String css_style_fpath = ViewConstants.CSS_PATH + Settings.getInstance().getStyle().toString();

            String css_style = Objects.requireNonNull(getClass().getResource(css_style_fpath)).toExternalForm();

            scene.getStylesheets().clear();
            scene.getRoot().getStylesheets().clear();

            scene.getStylesheets().add(css_style);
            scene.getRoot().setStyle("-fx-font-family: '" + general_font.getFamily() + "';");
        } catch (Exception ignore) {
        }
    }

    //Sets styles and font for the passed alert
    private void setStyleForAlert(Alert alert) throws NullPointerException {
        if (alert == null)
            throw new NullPointerException("Alert cannot be null");

        try {
            String css_style = Objects.requireNonNull(getClass().getResource(ViewConstants.CSS_PATH + Settings.getInstance().getStyle().toString())).toExternalForm();
            alert.getDialogPane().getStylesheets().clear();
            alert.getDialogPane().setStyle("-fx-font-family: '" + general_font.getFamily() + "';");
            alert.getDialogPane().getStylesheets().add(css_style);
        } catch (Exception ignore) {
        }
    }

    //Define what to do when the stage is closing
    private void stageClosingRules(WindowEvent event) {
        event.consume();

        doExitRequest();
    }

    //Gets the loaded fxml in the passed stage
    private String getLoadedFXML(Stage s) {  //returns the fileName of the current loaded FXML
        try {
            String value = (String) s.getProperties().get(LOADED_FXML_STAGE_PROPERTY);
            return (value != null) ? value : "";
        } catch (Exception e) {
            return "";
        }
    }

}
