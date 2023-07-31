/**
 * Home.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model;

import com.ernestocesario.mydata.model.messages.HomeMessages;
import com.ernestocesario.mydata.model.services.*;
import com.ernestocesario.mydata.utils.OperationResult;
import com.ernestocesario.mydata.utils.OperationStatus;
import com.ernestocesario.mydata.utils.UtilityStage;
import com.ernestocesario.mydata.utils.fileSpecs.FileData;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static com.ernestocesario.mydata.model.messages.HomeMessages.IMPOSSIBLE_ADD_FILES_ALREADY_IN;


//Home Model
public class Home {
    private static Home instance = null;
    private final String usernameH;
    private final ObservableList<FileData> observableFileList;
    private final SortedList<FileData> sortedFilesList;
    public final SimpleBooleanProperty componentsEnabledProperty;
    public final SimpleDoubleProperty progressValueProperty;
    public final SimpleBooleanProperty abortButtonVisibilityProperty;
    public final SimpleBooleanProperty logoutGrantedProperty;
    private final List<Service<?>> allServices;

    private TableUpdaterService tableUpdaterService = null;
    private TableSearchService<FileData> tableSearchService = null;
    private AddFilesService addFilesService = null;
    private RemoveFilesService removeFilesService = null;
    private DeleteFilesService deleteFilesService = null;
    private OpenFilesService openFilesService = null;
    private ProtectionFilesService protectionFilesService = null;
    private SenderFilesService senderFilesService = null;
    private ReceiverFilesService receiverFilesService = null;

    //Instance of components of the controller
    private final TableView<FileData> tableView;


    private Home(TableView<FileData> tableView) {
        this.tableView = tableView;

        usernameH = Session.getInstance().getUsernameH();
        observableFileList = FXCollections.observableArrayList();
        sortedFilesList = new SortedList<>(observableFileList);
        componentsEnabledProperty = new SimpleBooleanProperty(true);
        progressValueProperty = new SimpleDoubleProperty(0.0);
        abortButtonVisibilityProperty = new SimpleBooleanProperty(false);
        logoutGrantedProperty = new SimpleBooleanProperty(false);

        initTableUpdaterService();
        initSearchTableService();
        initAddFilesService();
        initRemoveFilesService();
        initDeleteFilesService();
        initProtectionFilesService();
        initShareFilesService();
        initOpenFilesService();

        allServices = new ArrayList<>();
        initAllServiceList();

        doUpdateTable();
    }


    //Instance manage methods
    public static Home getInstance() {
        return instance;
    }

    public static void newInstance(TableView<FileData> tableView) {
        instance = new Home(tableView);
    }

    public static boolean destroyInstance() {
        if (instance != null && instance.areImportantServiceRunning())
            return false;
        Session.getInstance().destroy();
        instance = null;
        return true;
    }


    public SortedList<FileData> getSortedFileList() {
        return sortedFilesList;
    }


    //Start services methods
    public void doUpdateTable() {
        tableUpdaterService.restart();
    }

    public void doSearchTable(TableColumn<FileData, ?> searchCol, String searchTerm) {
        if (areServicesRunning())
            return;

        tableSearchService.setSearchColumn(searchCol);
        tableSearchService.setSearchTerm(searchTerm);
        progressValueProperty.bind(tableSearchService.progressProperty());

        tableSearchService.restart();
    }

    public void doAddFiles(List<File> files) {
        if (areServicesRunning())
            return;

        componentsEnabledProperty.set(false);
        addFilesService.setFiles(files);
        progressValueProperty.bind(addFilesService.progressProperty());

        addFilesService.restart();

    }

    public void doRemoveFiles(ObservableList<FileData> items, ObservableList<Integer> indices) {
        if (areServicesRunning())
            return;

        componentsEnabledProperty.set(false);
        removeFilesService.setItemsOfTable(items);
        removeFilesService.setIndices(indices);
        progressValueProperty.bind(removeFilesService.progressProperty());

        removeFilesService.restart();

    }

    public void doDeleteFiles(ObservableList<FileData> items, ObservableList<Integer> indices) {
        if (areServicesRunning())
            return;

        componentsEnabledProperty.set(false);
        deleteFilesService.setItemsOfTable(items);
        deleteFilesService.setIndices(indices);
        progressValueProperty.bind(deleteFilesService.progressProperty());

        deleteFilesService.restart();

    }

    public void doProtectionFiles(ObservableList<FileData> items, ObservableList<Integer> indices, FileProtection fileProtection) {
        if (areServicesRunning())
            return;

        componentsEnabledProperty.set(false);
        protectionFilesService.setItemsOfTable(items);
        protectionFilesService.setIndices(indices);
        protectionFilesService.setNewProtection(fileProtection);
        progressValueProperty.bind(protectionFilesService.progressProperty());

        protectionFilesService.restart();

    }

    public boolean applyFileProtectionToAll(FileProtection fileProtection, EventHandler<WorkerStateEvent> event) {
        if (areServicesRunning())
            return false;

        protectionFilesService.setOnSucceeded(event);

        ObservableList<Integer> indices = FXCollections.observableArrayList();
        for (int i = 0; i < observableFileList.size(); ++i)
            indices.add(i);

        protectionFilesService.setItemsOfTable(observableFileList);
        protectionFilesService.setIndices(indices);
        protectionFilesService.setNewProtection(fileProtection);

        progressValueProperty.bind(protectionFilesService.progressProperty());
        protectionFilesService.restart();
        return true;
    }

    public void doOpenFiles(ObservableList<FileData> items, ObservableList<Integer> indices) {
        if (areServicesRunning())
            return;

        componentsEnabledProperty.set(false);
        openFilesService.setItemsOfTable(items);
        openFilesService.setIndices(indices);
        progressValueProperty.bind(openFilesService.progressProperty());

        openFilesService.restart();
    }

    public void doSendFiles(ObservableList<FileData> items, ObservableList<Integer> indices) {
        if (areServicesRunning())
            return;

        senderFilesService.reset();
        componentsEnabledProperty.set(false);
        senderFilesService.setItemsOfTable(items);
        senderFilesService.setIndices(indices);

        InetSocketAddress socketAddress = ViewSelector.getInstance().getNetworkAddressView().getResult();

        senderFilesService.setInetSocketAddress(socketAddress);

        if (socketAddress == null) {
            componentsEnabledProperty.set(true);
            return;
        }

        SenderCode.getInstance().setSenderFilesService(senderFilesService);
        SenderCode.getInstance().setInetSocketAddress(socketAddress);
        SenderCode.getInstance().setAbortButtonVisibilityProperty(abortButtonVisibilityProperty);

        progressValueProperty.bind(senderFilesService.progressProperty());

        UtilityStage<Boolean> senderCodeStage = ViewSelector.getInstance().getSenderCodeView();
        if (senderCodeStage.getResult() == null) {
            senderFilesService.cancel();
            progressValueProperty.unbind();
            progressValueProperty.set(0.0);
        }
    }

    public void doReceiveFiles(File pathToSave) {
        if (areServicesRunning())
            return;

        receiverFilesService.reset();
        componentsEnabledProperty.set(false);
        receiverFilesService.setPathToSave(pathToSave);

        UtilityStage<InetSocketAddress> receiverCodeStage = ViewSelector.getInstance().getReceiverCodeView();

        InetSocketAddress socketAddress = receiverCodeStage.getResult();
        if (socketAddress == null) {
            componentsEnabledProperty.set(true);
            return;
        }

        receiverFilesService.setInetSocketAddress(socketAddress);
        progressValueProperty.bind(receiverFilesService.progressProperty());

        receiverFilesService.restart();
        abortButtonVisibilityProperty.set(true);
    }


    //Stop Transfer services method
    public void stopTransferServices() {
        abortButtonVisibilityProperty.set(false);  //hide abort button
        if (senderFilesService.isRunning())
            senderFilesService.cancel();
        if (receiverFilesService.isRunning())
            receiverFilesService.cancel();
    }


    //Exit methods
    public boolean doLogoutRequest() {
        if (areServicesRunning())
            return false;

        componentsEnabledProperty.set(false);
        if (Settings.getInstance().getLockFilesOnClose() && !observableFileList.isEmpty())
            applyFileProtectionToAll(FileProtection.Locked, event -> logoutGrantedProperty.set(true));
        else
            logoutGrantedProperty.set(true);
        return true;
    }

    public boolean doExitRequest() {
        if (areServicesRunning())
            return false;

        componentsEnabledProperty.set(false);
        if (Settings.getInstance().getLockFilesOnClose() && !observableFileList.isEmpty())
            applyFileProtectionToAll(FileProtection.Locked, event -> ViewSelector.getInstance().exitNow());
        else
            ViewSelector.getInstance().exitNow();

        return true;
    }


    //Private methods
    private void initAllServiceList() {
        allServices.add(tableUpdaterService);
        allServices.add(tableSearchService);
        allServices.add(addFilesService);
        allServices.add(removeFilesService);
        allServices.add(deleteFilesService);
        allServices.add(openFilesService);
        allServices.add(protectionFilesService);
        allServices.add(senderFilesService);
        allServices.add(receiverFilesService);
    }

    private boolean areServicesRunning() {
        for (Service<?> service : allServices) {
            if (service.isRunning())
                return true;
        }
        return false;
    }

    public boolean areImportantServiceRunning() {
        for (Service<?> service : allServices) {
            if (service.isRunning() && !(service instanceof TableUpdaterService) && !(service instanceof TableSearchService<?>))
                return true;
        }
        return false;
    }


    //Services initializer
    private void initTableUpdaterService() {
        tableUpdaterService = new TableUpdaterService(usernameH);

        tableUpdaterService.setOnSucceeded(event -> {
            List<FileData> fileList = tableUpdaterService.getValue();
            observableFileList.clear();
            observableFileList.addAll(fileList);
            tableView.refresh();
        });
    }

    private void initSearchTableService() {
        tableSearchService = new TableSearchService<>(sortedFilesList);


        tableSearchService.setOnSucceeded(event -> {
            SortedList<FileData> sortedFilteredList = tableSearchService.getValue();
            tableView.setItems(sortedFilteredList);
            sortedFilteredList.comparatorProperty().bind(tableView.comparatorProperty());
        });
    }

    private void initAddFilesService() {
        addFilesService = new AddFilesService(usernameH);

        addFilesService.setOnSucceeded(event -> {
            doUpdateTable();

            Integer numFileAlreadyInDB = (Integer) event.getSource().getValue();
            if (numFileAlreadyInDB > 0)
                ViewSelector.getInstance().createAlert(numFileAlreadyInDB + IMPOSSIBLE_ADD_FILES_ALREADY_IN, Alert.AlertType.WARNING).showAndWait();

            componentsEnabledProperty.set(true);
        });
    }

    private void initRemoveFilesService() {
        removeFilesService = new RemoveFilesService();

        removeFilesService.setOnSucceeded(workerStateEvent -> {

            doUpdateTable();

            Integer numFileLocked = (Integer) workerStateEvent.getSource().getValue();
            if (numFileLocked > 0)
                ViewSelector.getInstance().createAlert(numFileLocked + HomeMessages.REMOVE_FAIL_FILE_LOCKED, Alert.AlertType.WARNING).showAndWait();
            componentsEnabledProperty.set(true);
        });
    }

    private void initDeleteFilesService() {
        deleteFilesService = new DeleteFilesService();

        deleteFilesService.setOnSucceeded(workerStateEvent -> {

            doUpdateTable();

            Integer numFilesCannotDelete = (Integer) workerStateEvent.getSource().getValue();
            if (numFilesCannotDelete > 0)
                ViewSelector.getInstance().createAlert(numFilesCannotDelete + HomeMessages.DELETE_FAIL_FILE, Alert.AlertType.WARNING).showAndWait();
            componentsEnabledProperty.set(true);
        });
    }

    private void initProtectionFilesService() {
        protectionFilesService = new ProtectionFilesService(Session.getInstance().getPassword());

        protectionFilesService.setOnSucceeded(workerStateEvent -> {
            doUpdateTable();
            Integer numFilesCannotModify = (Integer) workerStateEvent.getSource().getValue();
            if (numFilesCannotModify > 0)
                ViewSelector.getInstance().createAlert(numFilesCannotModify + HomeMessages.ENC_FAIL_FILE, Alert.AlertType.WARNING).showAndWait();
            componentsEnabledProperty.set(true);
        });
    }

    private void initShareFilesService() {
        senderFilesService = new SenderFilesService(Session.getInstance().getPassword());
        receiverFilesService = new ReceiverFilesService(usernameH);

        setOnSucceedFileTransferService(senderFilesService);
        setOnCancelledFileTransferService(senderFilesService);

        setOnSucceedFileTransferService(receiverFilesService);
        setOnCancelledFileTransferService(receiverFilesService);
    }

    private void initOpenFilesService() {
        openFilesService = new OpenFilesService(Session.getInstance().getPassword());

        openFilesService.setOnSucceeded(workerStateEvent -> {

            doUpdateTable();

            Integer numFilesCannotOpen = (Integer) workerStateEvent.getSource().getValue();
            if (numFilesCannotOpen > 0)
                ViewSelector.getInstance().createAlert(numFilesCannotOpen + HomeMessages.OPEN_FAIL_FILE, Alert.AlertType.WARNING).showAndWait();
            componentsEnabledProperty.set(true);
        });
    }

    private void setOnSucceedFileTransferService(Service<OperationResult<Integer>> service) {
        service.setOnSucceeded(workerStateEvent -> {
            abortButtonVisibilityProperty.set(false);
            doUpdateTable();

            @SuppressWarnings("unchecked")
            OperationResult<Integer> opRes = (OperationResult<Integer>) workerStateEvent.getSource().getValue();
            Integer numFilesTransferFailed = opRes.result();

            if (opRes.status() == OperationStatus.SUCCEED)
                ViewSelector.getInstance().createAlert(HomeMessages.TRANSFER_FILE_SUCCEED, Alert.AlertType.INFORMATION).showAndWait();
            else if (opRes.status() == OperationStatus.FAILED)
                ViewSelector.getInstance().createAlert(numFilesTransferFailed + HomeMessages.TRANSFER_FILE_FAIL, Alert.AlertType.ERROR).showAndWait();
            else
                ViewSelector.getInstance().createAlert(HomeMessages.TRANSFER_CANCELLED, Alert.AlertType.INFORMATION).showAndWait();

            componentsEnabledProperty.set(true);
        });
    }

    private void setOnCancelledFileTransferService(Service<OperationResult<Integer>> service) {
        service.stateProperty().addListener((observable, oldValue, newValue) -> {
            if ((oldValue == Worker.State.SUCCEEDED || oldValue == Worker.State.RUNNING) && newValue == Worker.State.CANCELLED) {
                abortButtonVisibilityProperty.set(false);
                doUpdateTable();
                ViewSelector.getInstance().createAlert(HomeMessages.TRANSFER_CANCELLED, Alert.AlertType.INFORMATION).showAndWait();
                componentsEnabledProperty.set(true);
            }
        });
    }
}
