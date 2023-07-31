/**
 * RegistrationMessages.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model.messages;

import static com.ernestocesario.mydata.model.constants.AccountConstants.*;

public class RegistrationMessages {
    private RegistrationMessages() {
    }


    //Registration results
    public static final String REGISTRATION_COMPLETED = """
            Registration Complete!
            Your password has been copied to the clipboard on your keyboard. Guard it well, the password if lost cannot be recovered!
            """;
    public static final String REGISTRATION_USER_ALREADY_EXISTS = "The username has already been taken. Try another one!";
    public static final String REGISTRATION_FAILED_CONSTRAINTS = "The username and/or password do not meet the requirements!";


    //Tooltip autoPassGen
    public static final String HELP_AUTOGEN_PASSWORD = "Auto-generate a safe password";


    //Field constraints prompt text
    public static final String USERNAME_FIELD_CREATION_PROMPT_TEXT = USERNAME_MIN_LEN + " - " + USERNAME_MAX_LEN + " chars";
    public static final String PASSWORD_FIELD_CREATION_PROMPT_TEXT = PASSWORD_MIN_LEN + " - " + PASSWORD_MAX_LEN + " chars";
}
