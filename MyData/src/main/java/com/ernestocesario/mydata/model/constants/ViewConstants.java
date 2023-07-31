/**
 * ViewConstants.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.constants;


import java.util.List;

public class ViewConstants {
    private ViewConstants() {
    }


    //FXMLs
    public static final String FXML_PATH = AppConstants.RESOURCES_PATH + "fxml/";


    //CSSs
    public static final String CSS_PATH = AppConstants.RESOURCES_PATH + "css/";
    public static final String CUPERTINO_STYLE = "Cupertino";
    public static final String NORD_STYLE = "Nord";
    public static final String PRIMER_STYLE = "Primer";


    //CSS List
    public static final List<String> CSS_LIST = List.of(CUPERTINO_STYLE, NORD_STYLE, PRIMER_STYLE);


    //Fonts
    public static final String FONTS_PATH = AppConstants.RESOURCES_PATH + "fonts/";
    public static final String HEAD_FONT = FONTS_PATH + "Lexend_Peta/LexendPeta-Bold.ttf";
    public static final String GENERAL_FONT = FONTS_PATH + "Roboto/Roboto-Regular.ttf";
    public static final String DISLEXYC_FONT_PATH = FONTS_PATH + "OpenDyslexic3/";
    public static final String DYSLEXIC_FONT_NOT_MACOS = DISLEXYC_FONT_PATH + "OpenDyslexic3-Regular.ttf";
    public static final String DYSLEXIC_FONT_MACOS = DISLEXYC_FONT_PATH + "._OpenDyslexic3-Regular.ttf";


    //Images
    public static final String IMAGES_PATH = AppConstants.RESOURCES_PATH + "images/";
    public static final String ICON_PATH = IMAGES_PATH + "icon.png";
    public static final String ICON_BLACK_PATH = IMAGES_PATH + "icon_Light.png";


    //Stage Transitions
    public static final int FADEIN_MILLISECONDS = 333;
    public static final int FADEOUT_MILLISECONDS = FADEIN_MILLISECONDS * 3;
}
