/**
 * SelectorNetworkInterfaceController.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.controller;

import com.ernestocesario.mydata.model.services.GetNetworkAddressService;
import com.ernestocesario.mydata.model.services.GetNetworkInterfaceService;
import com.ernestocesario.mydata.utils.utilityStageController.UtilityStageController;
import com.ernestocesario.mydata.utils.utilityStageController.UtilyStageCloseHandler;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.NetworkInterface;

import static com.ernestocesario.mydata.model.messages.NetworkInterfaceMessages.HELP_CHOOSE_NETWORK_INTERFACE;

public class SelectorNetworkInterfaceController implements UtilityStageController {
    private UtilyStageCloseHandler closeHandler;
    private GetNetworkInterfaceService getNetworkInterfaceService;
    private GetNetworkAddressService getNetworkAddressService;
    private ObservableList<NetworkInterface> observableNetworkInterface = null;


    @Override
    public void setCloseHandler(UtilyStageCloseHandler closeHandler) {
        this.closeHandler = closeHandler;
    }


    @FXML
    private Tooltip helpTooltip;

    @FXML
    private Label backIcon;

    @FXML
    private Label helpIcon;

    @FXML
    private ListView<NetworkInterface> listView;

    @FXML
    private Button selectBtn;

    @FXML
    private ProgressBar progressBar;


    @FXML
    void initialize() {
        Platform.runLater(() -> {
            helpTooltip.setText(HELP_CHOOSE_NETWORK_INTERFACE);
            allComponentsEnabled(false);
            initFieldBindedToSelection();
            initListView();
            initNetworkInterfaceService();
            initNetworkAddressService();
        });
    }

    @FXML
    void onBackClick(MouseEvent event) {
        getNetworkInterfaceService.cancel();
        closeHandler.handleStageClose(null);
    }

    @FXML
    void onSelectAction(ActionEvent event) {
        allComponentsEnabled(false);
        NetworkInterface selectedInterface = listView.getSelectionModel().getSelectedItem();
        if (selectedInterface != null) {
            getNetworkAddressService.setNetworkInterface(selectedInterface);
            progressBar.progressProperty().bind(getNetworkAddressService.progressProperty());
            getNetworkAddressService.restart();
        } else
            allComponentsEnabled(true);
    }


    //Private methods
    private void initFieldBindedToSelection() {
        selectBtn.setDisable(true);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean isSelection = newValue != null;
            selectBtn.setDisable(!isSelection);
        });
    }

    private void allComponentsEnabled(boolean val) {
        selectBtn.setDisable(!val);
        backIcon.setDisable(!val);
        listView.setDisable(!val);
        helpIcon.setDisable(!val);
    }

    private void initListView() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);  //only one element can be selected

        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(NetworkInterface item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getDisplayName());
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initNetworkInterfaceService() {
        getNetworkInterfaceService = new GetNetworkInterfaceService();

        getNetworkInterfaceService.setOnSucceeded(workerStateEvent -> {
            observableNetworkInterface = (ObservableList<NetworkInterface>) workerStateEvent.getSource().getValue();
            listView.setItems(observableNetworkInterface);
            allComponentsEnabled(true);
        });

        progressBar.progressProperty().bind(getNetworkInterfaceService.progressProperty());
        getNetworkInterfaceService.restart();
    }

    private void initNetworkAddressService() {
        getNetworkAddressService = new GetNetworkAddressService();

        getNetworkAddressService.setOnSucceeded(workerStateEvent -> {
            closeHandler.handleStageClose(workerStateEvent.getSource().getValue());
        });
    }
}
