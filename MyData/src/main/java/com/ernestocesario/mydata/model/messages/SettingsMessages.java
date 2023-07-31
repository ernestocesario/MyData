/**
 * SettingsMessages.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.messages;

public class SettingsMessages {
    private SettingsMessages() {
    }


    //Settings model message
    public static final String FAILED_RESET_SETTINGS_ERR = """
            Unable to reset settings due to an unexpected error.
            If the error recurs, make sure that other programs are not interfering with the operation of the application.
            """;
    public static final String FAILED_SAVE_SETTINGS_ERR = """
            Unable to save settings due to an unexpected error.
            If the error recurs, make sure that other programs are not interfering with the operation of the application.
            """;


    //Settings controller message
    public static final String PASSWORD_FAILED_CONSTRAINTS = "The new password do not meet the requirements!";
    public static final String ASK_DELETE_ACCOUNT = "Are you sure you want to delete the account?";
    public static final String APP_CLOSE_APPLY_SETTINGS = "The app will close to make the changes";
    public static final String CHANGE_PASSWORD_FILES_UNLOCKED = "All files will be unlocked to change passwords!\n" +
            "Next time you start the app re-lock the files manually";
    public static final String IMPOSSIBLE_CHANGE_PASSWORD_ONGOING_OPERATIONS = "Unable to change password at the moment due to ongoing operations.\n" +
            "Please try again later";
    public static final String CHANGE_PASSWORD_SUCCEED = "Your password has been changed and copied to the clipboard on your keyboard.\n" +
            "Guard it well, the password if lost cannot be recovered!";
    public static final String DELETE_ACCOUNT_SUCCEED = "The account has been deleted!\n" +
            "The app will close";

    public static final String DELETE_ACCOUNT_FAILED_CONTAINS_FILES = "Unable to delete as it contains files.\n" +
            "Remove all files from the account and try again";

    public static final String ABOUT_US_TEXT = """
            MyData
                        
            Developed by:
            Ernesto Cesario
                        
                        
            Resource used:
            This app uses "free" resources for which attribution is required for some.
            Below are the attributions of the resources that require it:
                        
            Mountain icons created by Freepik - Flaticon
            https://www.flaticon.com/free-icons/mountain
            """;
}
