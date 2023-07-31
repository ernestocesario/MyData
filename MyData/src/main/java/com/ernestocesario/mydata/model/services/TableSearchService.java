/**
 * TableSearchService.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.services;

import com.ernestocesario.mydata.model.tasks.TableSearchTask;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;

//Service to search in the available files of the current account
public class TableSearchService<T> extends Service<SortedList<T>> {
    private final ObservableList<T> data;
    private TableColumn<T, ?> searchColumn = null;
    private String searchTerm = null;


    public TableSearchService(ObservableList<T> data) {
        this.data = data;
    }

    public void setSearchColumn(TableColumn<T, ?> searchColumn) {
        this.searchColumn = searchColumn;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    protected Task<SortedList<T>> createTask() {
        return new TableSearchTask<>(data, searchColumn, searchTerm);
    }
}
