/**
 * FileSizeRepresentation.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.fileSpecs;


//Class enum used to represent various ways of expressing the size of a file
public enum FileSizeRepresentation {
    Byte,
    KiloByte,
    MegaByte,
    GigaByte,
    TeraByte,
    PetaByte;


    public String getAcronym() {
        StringBuilder acronym = new StringBuilder();
        for (char c : this.name().toCharArray())
            if (c >= 'A' && c <= 'Z')
                acronym.append(c);
        return acronym.toString();
    }

    //Returns for a value for which if the byte size of a file is divided, returns the value in the current representation
    public double getDivider() {
        return Math.pow(1024.0, this.ordinal());
    }
}
