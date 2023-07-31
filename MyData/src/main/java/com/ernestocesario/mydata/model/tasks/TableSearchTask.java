/**
 * TableSearchTask.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.tasks;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.scene.control.TableColumn;

import java.util.Objects;
import java.util.function.Predicate;

public class TableSearchTask<T> extends Task<SortedList<T>> {
    private record ItemFilter<T, C>(TableColumn<T, C> searchColumn, String searchTerm) implements Predicate<T> {
        @Override
        public boolean test(T item) {
            if (searchTerm == null || searchTerm.isEmpty())
                return true;

            C cellValue = searchColumn.getCellData(item);
            return cellValue != null && cellValue.toString().toLowerCase().contains(searchTerm.toLowerCase());
        }
    }


    private final ObservableList<T> data;
    private final TableColumn<T, ?> searchColumn;
    private final String searchTerm;


    public TableSearchTask(ObservableList<T> data, TableColumn<T, ?> searchColumn, String searchTerm) {
        this.data = Objects.requireNonNull(data);
        this.searchColumn = Objects.requireNonNull(searchColumn);
        this.searchTerm = Objects.requireNonNull(searchTerm);
    }

    @Override
    protected SortedList<T> call() {
        updateProgress(0, 1);
        ItemFilter<T, ?> filter = new ItemFilter<>(searchColumn, searchTerm);
        updateProgress(1, 1);
        return new SortedList<>(new FilteredList<>(data, filter));
    }
}
