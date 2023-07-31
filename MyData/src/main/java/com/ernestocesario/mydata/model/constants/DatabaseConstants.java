/**
 * DatabaseConstants.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.constants;

import java.util.List;

public class DatabaseConstants {
    private DatabaseConstants() {
    }


    //Database filepath
    public static final String DATABASE_PATH = AppConstants.APPDATA_PATH + "appdb.db";


    //Accounts
    public static final String ACCOUNTS_TBLNAME = "users";
    public static final String ACCOUNT_TBLNAME_USER_COL = "username";
    public static final String ACCOUNT_TBLNAME_PASS_COL = "password";


    //Files
    public static final String FILES_TBLNAME = "secureFiles";
    public static final String FILES_TBLNAME_USER_COL = "username";
    public static final String FILES_TBLNAME_FILEPATH_COL = "filepath";
    public static final String FILES_TBLNAME_PROTECTION_COL = "protection";


    //Creation Tables
    public static final String CREATE_TBL_ACCOUNTS_QUERY = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS_TBLNAME + " (" + ACCOUNT_TBLNAME_USER_COL +
            " TEXT PRIMARY KEY, " + ACCOUNT_TBLNAME_PASS_COL + " TEXT)";
    public static final String CREATE_TBL_FILES_QUERY = "CREATE TABLE IF NOT EXISTS " + FILES_TBLNAME + " (" + FILES_TBLNAME_USER_COL +
            " TEXT, " + FILES_TBLNAME_FILEPATH_COL + " TEXT PRIMARY KEY, " + FILES_TBLNAME_PROTECTION_COL + " TEXT)";
    public static final List<String> CREATE_TBL_QUERIES = List.of(CREATE_TBL_ACCOUNTS_QUERY, CREATE_TBL_FILES_QUERY);


    //Add new user
    public static final String ADD_USER_QUERY = "INSERT INTO " + ACCOUNTS_TBLNAME + " (" + ACCOUNT_TBLNAME_USER_COL + ", " + ACCOUNT_TBLNAME_PASS_COL +
            ") VALUES (?, ?)";
    //Add new protected file
    public static final String ADD_FILES_QUERY = "INSERT INTO " + FILES_TBLNAME + " (" + FILES_TBLNAME_USER_COL + ", " + FILES_TBLNAME_FILEPATH_COL +
            ", " + FILES_TBLNAME_PROTECTION_COL + ") VALUES (?, ?, ?)";
    //Update file protection
    public static final String UPDATE_FILE_PROTECTION_QUERY = "UPDATE " + FILES_TBLNAME + " SET " + FILES_TBLNAME_PROTECTION_COL + " = ? WHERE " +
            FILES_TBLNAME_FILEPATH_COL + " = ?";


    //Update user password
    public static final String UPDATE_USER_PASSWORD_QUERY = "UPDATE " + ACCOUNTS_TBLNAME + " SET " + ACCOUNT_TBLNAME_PASS_COL + " = ? WHERE " +
            ACCOUNT_TBLNAME_USER_COL + " = ?";


    //Get account data
    public static final String GET_USER_QUERY = "SELECT * FROM " + ACCOUNTS_TBLNAME + " WHERE " + ACCOUNT_TBLNAME_USER_COL + " = ?";
    //Get protected files
    public static final String GET_FILES_QUERY = "SELECT * FROM " + FILES_TBLNAME + " WHERE " + FILES_TBLNAME_USER_COL + " = ?";
    //Check if a file is already protected
    public static final String CHECK_FILE_ALREADY_IN = "SELECT * FROM " + FILES_TBLNAME + " WHERE " + FILES_TBLNAME_FILEPATH_COL + " = ?";


    //Remove account data
    public static final String REMOVE_USER_QUERY = "DELETE FROM " + ACCOUNTS_TBLNAME + " WHERE " + ACCOUNT_TBLNAME_USER_COL + " = ?";
    //Remove secureFile
    public static final String REMOVE_FILE_QUERY = "DELETE FROM " + FILES_TBLNAME + " WHERE " + FILES_TBLNAME_FILEPATH_COL + " = ?";
}
