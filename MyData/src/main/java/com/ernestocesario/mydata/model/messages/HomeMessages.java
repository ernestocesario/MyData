/**
 * HomeMessages.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.messages;

public class HomeMessages {
    private HomeMessages() {
    }


    public static final String GREETING_PREFIX = "Hi, ";
    public static final String TABLEVIEW_PLACEHOLDER_TEXT = "It looks a little empty here...";
    public static final String SEARCH_BY_BOX_PREFIX = "By: ";
    public static final String WARN_ABOUT_LOCKED_FILES_SELECTED = """
            Some of the selected files are locked and will not be processed.
            To process them unlock them, or set the corresponding switch in the settings to on to auto unlock the files before processing them.
            """;
    public static final String ASK_DELETE_FILES_SELECTED = "Are you sure you want to permanently delete the selected files?";
    public static final String ASK_REMOVE_FILES_SELECTED = "Are you sure you want to remove selected files from the application?";


    //Exit error message
    public static final String IMPOSSIBLE_EXIT_ONGOING_OPERATIONS = """
            Unable to exit the application, due to ongoing operations!
            Wait for operations to finish and try again
            """;


    //Service return message
    public static final String IMPOSSIBLE_ADD_FILES_ALREADY_IN = " of the selected files cannot be added as they are already in the application database!\n" +
            "If you do not see these files in your account, it means that they have been added to another account.";
    public static final String REMOVE_FAIL_FILE_LOCKED = " of the selected files cannot be removed because they are protected!";
    public static final String DELETE_FAIL_FILE = " of the selected files cannot be deleted from the computer!\n" + "Check that the files are not in use and try again.";
    public static final String OPEN_FAIL_FILE = " of the selected files cannot be opened by the system!";
    public static final String ENC_FAIL_FILE = " of the selected files cannot be changed!\n" + "Check that the files are not in use and try again.";
    public static final String TRANSFER_FILE_FAIL = " of the files were not transferred due to a network error!\n" + "If the error persists, check the network connection.";
    public static final String TRANSFER_FILE_SUCCEED = "File transfer completed successfully!";


    //Receiver Code
    public static final String TRANSFER_CODE_INVALID = "Oops. It seems that the transfer code entered is invalid! Please try again";


    //Share files
    public static final String TRANSFER_CANCELLED = "File transfer has been cancelled";
}
