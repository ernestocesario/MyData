/**
 * Database.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model;

import com.ernestocesario.mydata.model.messages.GeneralMessages;
import com.ernestocesario.mydata.utils.Pair;
import com.ernestocesario.mydata.utils.fileSpecs.FileProtection;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ernestocesario.mydata.model.constants.DatabaseConstants.*;


//Class used to interact with the database quickly
public class Database {
    private static final Database instance = new Database();


    public static void initialize() {
    } //initialization on demand holder idiom (IDH)

    public static Database getInstance() {
        return instance;
    }


    //Add wrappers
    public void addUser(String usernameH, String passwordH) {
        genericModifyQuery(ADD_USER_QUERY, usernameH, passwordH);
    }

    public void addSecureFile(String usernameH, String filepath, FileProtection fileProtection) {
        genericModifyQuery(ADD_FILES_QUERY, usernameH, filepath, fileProtection.toString());
    }


    //Update wrappers
    public int updateFileProtection(String filePath, FileProtection newProtection) {
        return genericModifyQuery(UPDATE_FILE_PROTECTION_QUERY, newProtection.toString(), filePath);
    }

    public int updateUserPassword(String usernameH, String newPasswordH) {
        return genericModifyQuery(UPDATE_USER_PASSWORD_QUERY, newPasswordH, usernameH);
    }


    //Get wrappers
    public String getPasswordByUser(String usernameH) {
        List<String> res = genericUnarySelectQuery(GET_USER_QUERY, ACCOUNT_TBLNAME_PASS_COL, usernameH);
        return (res.size() > 0) ? res.get(0) : null;
    }

    public Pair<List<String>, List<String>> getSecureFilesByUser(String usernameH) {
        List<String> paths = genericUnarySelectQuery(GET_FILES_QUERY, FILES_TBLNAME_FILEPATH_COL, usernameH);
        List<String> protections = genericUnarySelectQuery(GET_FILES_QUERY, FILES_TBLNAME_PROTECTION_COL, usernameH);
        return new Pair<>(paths, protections);
    }

    public Pair<Integer, Integer> getAccountInfoByUser(String usernameH) {
        List<String> protections = genericUnarySelectQuery(GET_FILES_QUERY, FILES_TBLNAME_PROTECTION_COL, usernameH);

        int numFilesProtected = 0;
        for (var item : protections)
            if (FileProtection.valueOf(item) == FileProtection.Locked)
                ++numFilesProtected;

        return new Pair<>(protections.size(), numFilesProtected);
    }

    public boolean checkFileAlreadyIn(String filePath) {
        List<String> res = genericUnarySelectQuery(CHECK_FILE_ALREADY_IN, FILES_TBLNAME_FILEPATH_COL, filePath);
        return res.size() > 0;
    }


    //Remove wrappers
    public int removeUser(String usernameH) {
        return genericModifyQuery(REMOVE_USER_QUERY, usernameH);
    }

    public int removeSecureFileByPath(String filePath) {
        return genericModifyQuery(REMOVE_FILE_QUERY, filePath);
    }


    //Private Methods
    private Database() {
        try (Connection connection = createDBConnection(); Statement statement = connection.createStatement()) {
            for (String query : CREATE_TBL_QUERIES) {
                statement.executeUpdate(query);
            }
        } catch (Exception e) {
            fatalDatabaseError();
        }
    }

    private int genericModifyQuery(String insertQuery, String... params) {
        int rowsAffected;
        try (Connection connection = createDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            int paramIndex = 1;
            for (String p : params)
                preparedStatement.setString(paramIndex++, p);
            rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected;
        } catch (Exception e) {
            fatalDatabaseError();
            return 0;
        }
    }

    private List<String> genericUnarySelectQuery(String selectQuery, String returnColName, String... params) {
        List<String> result = new ArrayList<>();
        try (Connection connection = createDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            int paramIndex = 1;
            for (String p : params)
                preparedStatement.setString(paramIndex++, p);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next())
                    result.add(resultSet.getString(returnColName));
            }
        } catch (Exception e) {
            fatalDatabaseError();
        }
        return result;
    }

    private Connection createDBConnection() throws ClassNotFoundException, SQLException {  //Connect with the DB
        return DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
    }

    private void fatalDatabaseError() {
        Platform.runLater(() -> {  //because all the operation are done in an external thread
            ViewSelector.getInstance().createAlert(GeneralMessages.FATAL_ERR, Alert.AlertType.ERROR).showAndWait();
            System.exit(1);
        });
    }
}