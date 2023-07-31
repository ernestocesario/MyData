/**
 * FileData.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.utils.fileSpecs;

import com.ernestocesario.mydata.utils.Pair;

import java.io.File;


//Class used to represent File according to application needs.
public class FileData {
    private final String name;
    private final String type;
    private double size;
    private final String path;
    private FileProtection protection;


    public FileData(String name, String type, double size, String path, FileProtection protection) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.path = path;
        this.protection = protection;
    }


    //Setters
    public void setSize(double size) {
        this.size = size;
    }

    public void setProtection(FileProtection protection) {
        this.protection = protection;
    }


    //Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public FileProtection getProtection() {
        return protection;
    }

    public String getEntireName() {
        return name + "." + type;
    }


    //Public static methods

    //Returns an instance of FileData for the passed file arguments
    public static FileData getFileDataFromPath(String path, FileProtection protection, FileSizeRepresentation sizeRepresentation, int decimalDigits) {
        File file = new File(path);

        if (!file.exists())
            return null;

        String entireName = file.getName();

        String fileName;
        String fileType;

        int lastDotIndex = entireName.lastIndexOf(".");
        if (lastDotIndex > 0 && entireName.length() > lastDotIndex + 1) {
            fileName = entireName.substring(0, lastDotIndex);
            fileType = entireName.substring(lastDotIndex + 1);
        } else {
            fileName = entireName;
            fileType = "";
        }

        double fileSizeMb = roundFileSize(convertFileSize(file.length(), sizeRepresentation), decimalDigits);

        return new FileData(fileName, fileType, fileSizeMb, path, protection);
    }

    ////Returns a pair of file name and file extension
    public static Pair<String, String> splitNameFromExtension(String entireName) {
        Pair<String, String> res = new Pair<>();

        int lastDotIndex = entireName.lastIndexOf(".");
        if (lastDotIndex > 0 && entireName.length() > lastDotIndex + 1) {
            res.first = entireName.substring(0, lastDotIndex);
            res.second = entireName.substring(lastDotIndex + 1);
        } else {
            res.first = entireName;
            res.second = "";
        }
        return res;
    }

    //Returns the real size in byte of a file passed
    public static long getRealSize(String path) {
        File file = new File(path);
        if (!file.exists())
            return -1;

        return file.length();
    }


    //Private methods
    private static double convertFileSize(double fileSize, FileSizeRepresentation sizeRepresentation) {
        return fileSize / sizeRepresentation.getDivider();
    }

    //Returns the file size (based on the current representation) rounded to N decimal places
    private static double roundFileSize(double fileSize, int decimalDigits) {
        double v = Math.pow(10, decimalDigits);
        return Math.round(fileSize * v) / v;
    }
}
