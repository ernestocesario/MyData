/**
 * Session.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model;


//Singleton class used to represent the current session
public class Session {
    private static final Session instance = new Session();
    private String username, usernameH, password;

    private Session() {
    }

    public static Session getInstance() {
        return instance;
    }

    public void create(String username, String usernameH, String password) {
        this.username = username;
        this.usernameH = usernameH;
        this.password = password;
    }

    public void destroy() {
        username = null;
        usernameH = null;
        password = null;
    }

    public String getUsername() {
        return username;
    }

    public String getUsernameH() {
        return usernameH;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
