/**
 * AppConstants.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.constants;

public class AppConstants {
    private AppConstants() {
    }


    //Application name
    public static final String APP_NAME = "MyData";

    //Current directory
    public static final String CURRENT_PATH = System.getProperty("user.dir") + "/";

    //Data directory
    public static final String APPDATA_PATH = CURRENT_PATH + "appdata/";

    //Resources path
    public static final String RESOURCES_PATH = "/com/ernestocesario/mydata/";

    //Single instance checker file
    public static final String SINGLE_INSTANCE_CHECKER_FILE_PATH = APPDATA_PATH + "sifc";
}
