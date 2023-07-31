/**
 * ResourceGetter.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils;

import com.ernestocesario.mydata.model.Settings;

import java.io.InputStream;
import java.net.URL;

//Class to get the appropriate resource based on the current theme (defined by the Theme utility class)
public class ResourceGetter {
    private ResourceGetter() {
    } //No instantiation


    public static URL get(String filePath) throws NullPointerException {  //returns URL of the resource based on the current theme
        return ResourceGetter.class.getResource(getComputedFilePath(filePath));
    }

    public static InputStream getAsStream(String filePath) throws NullPointerException {  //returns an InputStream of the resource based on the current theme
        return ResourceGetter.class.getResourceAsStream(getComputedFilePath(filePath));
    }


    //Private Methods
    private static String getComputedFilePath(String filePath) {  //returns the correct filePath of the resource based on the current theme
        StringBuilder sb = new StringBuilder(filePath);
        int indexFileExtension = findIndexFileExtension(filePath);
        sb.insert(indexFileExtension, getThemeFileConvention());
        return sb.toString();
    }

    private static int findIndexFileExtension(String filePath) {  //returns the index of the "." in the file extension. if there is no extension, returns the filePath length
        for (int i = filePath.length() - 1; i >= 0; --i)
            if (filePath.charAt(i) == '.')
                return i;
        return filePath.length();
    }

    //returns a string based on the convention of calling a resource based on the light or dark theme,
    // with the strings "_light" or "_dark" before the fileName extension based on the current theme
    private static String getThemeFileConvention() {
        return "_" + Settings.getInstance().getStyle().getTheme().toString();
    }
}
