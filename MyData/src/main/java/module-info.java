/**
 * module-info.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
module com.ernestocesario.mydata {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.ernestocesario.truepasswordfield;
    requires org.bouncycastle.provider;
    requires spring.security.crypto;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;


    opens com.ernestocesario.mydata to javafx.fxml;
    exports com.ernestocesario.mydata;

    opens com.ernestocesario.mydata.controller;
    exports com.ernestocesario.mydata.controller;

    opens com.ernestocesario.mydata.utils.fileSpecs;
    exports com.ernestocesario.mydata.utils.fileSpecs;

    opens com.ernestocesario.mydata.utils;
    exports com.ernestocesario.mydata.utils;

    opens com.ernestocesario.mydata.model.services;
    exports com.ernestocesario.mydata.model.services;

    opens com.ernestocesario.mydata.utils.utilityStageController;
    exports com.ernestocesario.mydata.utils.utilityStageController;

    opens com.ernestocesario.mydata.model;
    exports com.ernestocesario.mydata.model;

    opens com.ernestocesario.mydata.utils.styles;
    exports com.ernestocesario.mydata.utils.styles;
}