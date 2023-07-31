/**
 * HomeController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.Home;
import com.ernestocesario.mydata.model.Session;
import com.ernestocesario.mydata.model.Settings;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import com.ernestocesario.mydata.utils.fileSpecs.FileSizeRepresentation;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.util.List;

import static com.ernestocesario.mydata.model.messages.HomeMessages.*;

public class HomeController {
    //Local Constants
    private final int FONTSIZE_ICON_PLACEHOLDER = 48;
    private final int FONTSIZE_TEXT_PLACEHOLDER = 26;


    @FXML
    private TableView<FileData> tableView;

    @FXML
    private TableColumn<FileData, String> filenameCol;

    @FXML
    private TableColumn<FileData, String> typeCol;

    @FXML
    private TableColumn<FileData, Double> sizeCol;

    @FXML
    private TableColumn<FileData, String> filepathCol;

    @FXML
    private TableColumn<FileData, FileProtection> protectionCol;

    @FXML
    private Label sessionUsernameLabel;

    @FXML
    private Button addBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> searchByBox;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button abortBtn;

    @FXML
    private SplitMenuButton protectionBtn;

    @FXML
    private MenuItem lockMenuItem;

    @FXML
    private MenuItem unlockMenuItem;

    @FXML
    private SplitMenuButton shareBtn;

    @FXML
    private MenuItem sendFilesBtn;

    @FXML
    private MenuItem receiveFilesBtn;

    @FXML
    private Button openBtn;

    @FXML
    private ProgressBar progressBar;


    @FXML
    void initialize() {
        Home.newInstance(tableView);
        sessionUsernameLabel.setText(GREETING_PREFIX + Session.getInstance().getUsername());
        progressBar.progressProperty().bind(Home.getInstance().progressValueProperty);
        initFilesTableView();
        initSearchFields();
        initComponentsEnabled();
        initFieldBindedToSelection();
    }

    @FXML
    void onAddAction(ActionEvent event) {
        List<File> files = ViewSelector.getInstance().openFilesChooser();
        if (files == null)
            return;

        Home.getInstance().doAddFiles(files);
    }

    @FXML
    void onRemoveAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        if (Settings.getInstance().getAskBeforeRemoveFiles()) {
            Alert confirmationAlert = ViewSelector.getInstance().createAlert(ASK_REMOVE_FILES_SELECTED, Alert.AlertType.CONFIRMATION, ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait();
            if (confirmationAlert.getResult() != ButtonType.YES)
                return;
        }

        Home.getInstance().doRemoveFiles(items, indices);
    }

    @FXML
    void onDeleteAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        if (Settings.getInstance().getAskBeforeDeleteFiles()) {
            Alert confirmationAlert = ViewSelector.getInstance().createAlert(ASK_DELETE_FILES_SELECTED, Alert.AlertType.CONFIRMATION, ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait();
            if (confirmationAlert.getResult() != ButtonType.YES)
                return;
        }

        Home.getInstance().doDeleteFiles(items, indices);
    }

    @FXML
    void onLogoutAction(ActionEvent event) {
        Home.getInstance().logoutGrantedProperty.addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                return;

            boolean v = Home.destroyInstance();
            ViewSelector.getInstance().loadLoginView();
        });

        if (!Home.getInstance().doLogoutRequest())
            ViewSelector.getInstance().createAlert(IMPOSSIBLE_EXIT_ONGOING_OPERATIONS, Alert.AlertType.WARNING).showAndWait();
    }

    @FXML
    void onSettingsAction(ActionEvent event) {
        if (Home.getInstance().areImportantServiceRunning())
            return;

        Home.getInstance().componentsEnabledProperty.set(false);
        tableView.getSelectionModel().clearSelection();
        if (ViewSelector.getInstance().loadSettingsView().getResult() == null)
            Home.getInstance().componentsEnabledProperty.set(true);
    }

    @FXML
    void onAbortAction(ActionEvent event) {
        Home.getInstance().stopTransferServices();
    }

    @FXML
    void onLockAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        Home.getInstance().doProtectionFiles(items, indices, FileProtection.Locked);
    }

    @FXML
    void onUnlockAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        Home.getInstance().doProtectionFiles(items, indices, FileProtection.Unlocked);
    }

    @FXML
    void onSendFilesAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        if (!Settings.getInstance().getAutoUnlockFilesToSend())
            warnAboutLockedFileSelected(items, indices);

        Home.getInstance().doSendFiles(items, indices);
    }

    @FXML
    void onReceiveFilesAction(ActionEvent event) {
        File pathToSave = ViewSelector.getInstance().saveDirectoryChooser();
        if (pathToSave == null)
            return;

        Home.getInstance().doReceiveFiles(pathToSave);
    }

    @FXML
    void onOpenAction(ActionEvent event) {
        ObservableList<FileData> items = tableView.getItems();
        ObservableList<Integer> indices = tableView.getSelectionModel().getSelectedIndices();

        if (!Settings.getInstance().getAutoUnlockFilesToOpen())
            warnAboutLockedFileSelected(items, indices);

        Home.getInstance().doOpenFiles(items, indices);
    }


    //Private Functions
    private VBox createTablePlaceholder() {
        FontIcon icon = new FontIcon("fas-kiwi-bird");
        icon.setIconSize(FONTSIZE_ICON_PLACEHOLDER);

        Label label = new Label();
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);
        label.setFont(Font.font(ViewSelector.getInstance().general_font.getFamily(), FONTSIZE_TEXT_PLACEHOLDER));
        label.setText(TABLEVIEW_PLACEHOLDER_TEXT);

        VBox vbox = new VBox(icon, label);
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    private void initFilesTableView() {
        filenameCol.setReorderable(false);
        typeCol.setReorderable(false);
        sizeCol.setReorderable(false);
        filepathCol.setReorderable(false);
        protectionCol.setReorderable(false);
        tableView.placeholderProperty().setValue(createTablePlaceholder());
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        FileSizeRepresentation sizeRepresentation = Settings.getInstance().getFileSizeRepresentation();
        sizeCol.setText(sizeCol.getText() + " (" + sizeRepresentation.getAcronym() + ")");

        tableView.setItems(Home.getInstance().getSortedFileList());
        Home.getInstance().getSortedFileList().comparatorProperty().bind(tableView.comparatorProperty());

        filenameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        filepathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
        protectionCol.setCellValueFactory(new PropertyValueFactory<>("protection"));
    }


    private void initSearchFields() {
        for (TableColumn<FileData, ?> column : tableView.getColumns())
            searchByBox.getItems().add(SEARCH_BY_BOX_PREFIX + column.getText());

        searchByBox.getSelectionModel().selectFirst();

        searchField.textProperty().addListener(observable -> {
            TableColumn<FileData, ?> searchCol = tableView.getColumns().get(searchByBox.getSelectionModel().getSelectedIndex());
            Home.getInstance().doSearchTable(searchCol, searchField.getText());
        });

        searchByBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            searchField.setText(searchField.getText());  //only to update the search when changing categories
        });
    }


    private void initFieldBindedToSelection() {
        removeBtn.setDisable(true);
        deleteBtn.setDisable(true);
        protectionBtn.setDisable(true);
        sendFilesBtn.setDisable(true);
        openBtn.setDisable(true);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isSelection = newValue != null;
            updateFunctionalComponents(isSelection);
        });

    }

    private void initComponentsEnabled() {
        Home.getInstance().componentsEnabledProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                enableFreeComponents();
            else
                allComponentsDisabled();
        });

        abortBtn.setDisable(true);
        abortBtn.setVisible(false);
        Home.getInstance().abortButtonVisibilityProperty.addListener((observable, oldValue, newValue) -> {
            abortBtn.setDisable(!newValue);
            abortBtn.setVisible(newValue);
        });
    }

    private void allComponentsDisabled() {
        searchField.setDisable(true);
        searchByBox.setDisable(true);
        tableView.setDisable(true);
        addBtn.setDisable(true);
        removeBtn.setDisable(true);
        deleteBtn.setDisable(true);
        logoutBtn.setDisable(true);
        settingsBtn.setDisable(true);
        protectionBtn.setDisable(true);
        shareBtn.setDisable(true);
        openBtn.setDisable(true);
    }

    private void enableFreeComponents() {
        searchField.setDisable(false);
        searchByBox.setDisable(false);
        tableView.setDisable(false);
        addBtn.setDisable(false);
        logoutBtn.setDisable(false);
        settingsBtn.setDisable(false);
        shareBtn.setDisable(false);

        updateFunctionalComponents(!tableView.getSelectionModel().isEmpty());
    }

    private void updateFunctionalComponents(boolean isSelection) {
        removeBtn.setDisable(!isSelection);
        deleteBtn.setDisable(!isSelection);
        protectionBtn.setDisable(!isSelection);
        sendFilesBtn.setDisable(!isSelection);
        openBtn.setDisable(!isSelection);
    }


    private void warnAboutLockedFileSelected(ObservableList<FileData> items, ObservableList<Integer> indices) {
        for (var i : indices) {
            FileData filedata = items.get(i);
            if (filedata.getProtection() == FileProtection.Locked) {
                ViewSelector.getInstance().createAlert(WARN_ABOUT_LOCKED_FILES_SELECTED, Alert.AlertType.WARNING).showAndWait();
                return;
            }
        }
    }
}