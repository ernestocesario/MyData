/**
 * StyleCSS.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.styles;


//Class used to represent styles (CSS) that can be applied to the app.
public class StyleCSS {
    private static final String sep = "_";
    private final String name;
    private final Theme theme;


    public StyleCSS(String name, Theme theme) {
        this.name = name;
        this.theme = theme;
    }

    //Getters
    public String getName() {
        return name;
    }

    public Theme getTheme() {
        return theme;
    }


    //Returns the CSS filename of the style based on the arguments passed
    public static String computeFileName(String name, Theme theme) {
        return name + sep + theme.toString() + ".css";
    }

    @Override
    public String toString() {
        return name + sep + theme + ".css";
    }
}
