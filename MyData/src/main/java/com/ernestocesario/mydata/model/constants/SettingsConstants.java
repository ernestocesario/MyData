/**
 * SettingsConstants.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.constants;

import com.ernestocesario.mydata.utils.fileSpecs.FileSizeRepresentation;
import com.ernestocesario.mydata.utils.styles.Theme;

public class SettingsConstants {
    private SettingsConstants() {
    }


    //Configuration file
    public static final String CONFIG_PATH = AppConstants.APPDATA_PATH + "config.cfg";


    //Settings Index
    public static final int IDX_STYLE_NAME = 0;
    public static final int IDX_STYLE_THEME = 1;
    public static final int IDX_SIZE_REPRESENTATION = 2;
    public static final int IDX_SIZE_DECIMAL_DIGIT = 3;
    public static final int IDX_ASK_BEFORE_REMOVE_FILES = 4;
    public static final int IDX_ASK_BEFORE_DELETE_FILES = 5;
    public static final int IDX_LOCK_FILES_ON_CLOSE = 6;
    public static final int IDX_AUTOUNLOCK_FILES_TO_OPEN = 7;
    public static final int IDX_AUTOUNLOCK_FILES_TO_SEND = 8;
    public static final int IDX_USE_DYSLEXIC_FONT = 9;


    // Default Settings
    public static final String DEFAULT_STYLE_NAME = ViewConstants.CUPERTINO_STYLE;
    public static final Theme DEFAULT_THEME = Theme.Light;
    public static final FileSizeRepresentation DEFAULT_FILESIZE_REPRESENTATION = FileSizeRepresentation.MegaByte;
    public static final int DEFAULT_FILESIZE_ROUND_DECIMAL_DIGITS = 2;
    public static final boolean DEFAULT_ASK_BEFORE_REMOVE_FILES = false;
    public static final boolean DEFAULT_ASK_BEFORE_DELETE_FILES = true;
    public static final boolean DEFAULT_LOCK_FILES_ON_CLOSE = false;
    public static final boolean DEFAULT_AUTOUNLOCK_FILES_TO_OPEN = true;
    public static final boolean DEFAULT_AUTOUNLOCK_FILES_TO_SEND = false;
    public static final boolean DEFAULT_USE_DYSLEXIC_FONT = false;


    //Settings Controller
    public static final int NUM_CONFIRMATION_DELETE_ACCOUNT = 3;
}
