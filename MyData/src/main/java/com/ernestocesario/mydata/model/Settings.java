/**
 * Settings.java
 * Copyright (C) 2023 Ernesto Cesario
 *
 * This file is part of MyData.
 * For the terms of the license, see the LICENSE file in the root of the repository.
 */
package com.ernestocesario.mydata.model;

import com.ernestocesario.mydata.model.constants.AppConstants;
import com.ernestocesario.mydata.model.constants.SettingsConstants;
import com.ernestocesario.mydata.model.messages.SettingsMessages;
import com.ernestocesario.mydata.utils.fileSpecs.FileSizeRepresentation;
import com.ernestocesario.mydata.utils.styles.StyleCSS;
import com.ernestocesario.mydata.utils.styles.Theme;
import com.ernestocesario.mydata.view.ViewSelector;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.ernestocesario.mydata.model.constants.SettingsConstants.*;


//Settings model
public class Settings {
    private static final Settings instance = new Settings();

    private final Path config_path = Path.of(SettingsConstants.CONFIG_PATH);

    //Settings
    private StyleCSS style;
    private FileSizeRepresentation fileSizeRepresentation;
    private int fileSizeRoundDecimalDigits;
    private boolean askBeforeRemoveFiles;
    private boolean askBeforeDeleteFiles;
    private boolean lockFilesOnClose;
    private boolean autoUnlockFilesToOpen;
    private boolean autoUnlockFilesToSend;
    private boolean useDyslexicFont;


    public static void initialize() {
    }  //IDH

    public static Settings getInstance() {
        return instance;
    }


    //Getters
    public StyleCSS getStyle() {
        return style;
    }

    public FileSizeRepresentation getFileSizeRepresentation() {
        return fileSizeRepresentation;
    }

    public int getFileSizeRoundDecimalDigits() {
        return fileSizeRoundDecimalDigits;
    }

    public Boolean getAskBeforeRemoveFiles() {
        return askBeforeRemoveFiles;
    }

    public Boolean getAskBeforeDeleteFiles() {
        return askBeforeDeleteFiles;
    }

    public Boolean getLockFilesOnClose() {
        return lockFilesOnClose;
    }

    public Boolean getAutoUnlockFilesToOpen() {
        return autoUnlockFilesToOpen;
    }

    public Boolean getAutoUnlockFilesToSend() {
        return autoUnlockFilesToSend;
    }

    public Boolean getUseDyslexicFont() {
        return useDyslexicFont;
    }


    //Setter
    public void setStyleName(String styleName) {
        style = new StyleCSS(styleName, style.getTheme());
    }

    public void setStyleTheme(Theme theme) {
        style = new StyleCSS(style.getName(), theme);
    }

    public void setFileSizeRepresentation(FileSizeRepresentation fileSizeRepresentation) {
        this.fileSizeRepresentation = fileSizeRepresentation;
    }

    public void setFileSizeRoundDecimalDigits(int fileSizeRoundDecimalDigits) {
        this.fileSizeRoundDecimalDigits = fileSizeRoundDecimalDigits;
    }

    public void setAskBeforeRemoveFiles(boolean askBeforeRemoveFiles) {
        this.askBeforeRemoveFiles = askBeforeRemoveFiles;
    }

    public void setAskBeforeDeleteFiles(boolean askBeforeDeleteFiles) {
        this.askBeforeDeleteFiles = askBeforeDeleteFiles;
    }

    public void setLockFilesOnClose(boolean lockFilesOnClose) {
        this.lockFilesOnClose = lockFilesOnClose;
    }

    public void setAutoUnlockFilesToOpen(boolean autoUnlockFilesToOpen) {
        this.autoUnlockFilesToOpen = autoUnlockFilesToOpen;
    }

    public void setAutoUnlockFilesToSend(boolean autoUnlockFilesToSend) {
        this.autoUnlockFilesToSend = autoUnlockFilesToSend;
    }

    public void setUseDyslexicFont(boolean useDyslexicFont) {
        this.useDyslexicFont = useDyslexicFont;
    }


    public void resetSettings() {
        if (Files.exists(config_path)) {
            try {
                Files.delete(config_path);
            } catch (Exception e) {
                ViewSelector.getInstance().createAlert(SettingsMessages.FAILED_RESET_SETTINGS_ERR, Alert.AlertType.ERROR).showAndWait();
                return;
            }
        }
        loadDefaultSettings();
    }

    //Save the settings on file
    public void saveSettings() {
        try {
            if (!checkAppdataDirectory())
                throw new IOException();

            Files.writeString(config_path, style.getName() + "\n" +
                    style.getTheme().toString() + "\n" +
                    fileSizeRepresentation.toString() + "\n" +
                    fileSizeRoundDecimalDigits + "\n" +
                    askBeforeRemoveFiles + "\n" +
                    askBeforeDeleteFiles + "\n" +
                    lockFilesOnClose + "\n" +
                    autoUnlockFilesToOpen + "\n" +
                    autoUnlockFilesToSend + "\n" +
                    useDyslexicFont);

        } catch (Exception ignore) {
            ViewSelector.getInstance().createAlert(SettingsMessages.FAILED_SAVE_SETTINGS_ERR, Alert.AlertType.ERROR).showAndWait();
        }
    }


    //Private functions
    private Settings() {
        loadSettings();
        Database.initialize();
    }


    //Loads settings from file
    private void loadSettings() {
        try {
            List<String> loadedSettings = Files.readAllLines(config_path).stream().toList();

            style = new StyleCSS(loadedSettings.get(IDX_STYLE_NAME), Theme.valueOf(loadedSettings.get(IDX_STYLE_THEME)));
            fileSizeRepresentation = FileSizeRepresentation.valueOf(loadedSettings.get(IDX_SIZE_REPRESENTATION));
            fileSizeRoundDecimalDigits = Integer.parseInt(loadedSettings.get(IDX_SIZE_DECIMAL_DIGIT));
            askBeforeRemoveFiles = Boolean.parseBoolean(loadedSettings.get(IDX_ASK_BEFORE_REMOVE_FILES));
            askBeforeDeleteFiles = Boolean.parseBoolean(loadedSettings.get(IDX_ASK_BEFORE_DELETE_FILES));
            lockFilesOnClose = Boolean.parseBoolean(loadedSettings.get(IDX_LOCK_FILES_ON_CLOSE));
            autoUnlockFilesToOpen = Boolean.parseBoolean(loadedSettings.get(IDX_AUTOUNLOCK_FILES_TO_OPEN));
            autoUnlockFilesToSend = Boolean.parseBoolean(loadedSettings.get(IDX_AUTOUNLOCK_FILES_TO_SEND));
            useDyslexicFont = Boolean.parseBoolean(loadedSettings.get(IDX_USE_DYSLEXIC_FONT));
        } catch (Exception ignore) {
            loadDefaultSettings();
            saveSettings();
        }
    }

    //Loads default settings
    private void loadDefaultSettings() {
        style = new StyleCSS(DEFAULT_STYLE_NAME, DEFAULT_THEME);
        fileSizeRepresentation = DEFAULT_FILESIZE_REPRESENTATION;
        fileSizeRoundDecimalDigits = DEFAULT_FILESIZE_ROUND_DECIMAL_DIGITS;
        askBeforeRemoveFiles = DEFAULT_ASK_BEFORE_REMOVE_FILES;
        askBeforeDeleteFiles = DEFAULT_ASK_BEFORE_DELETE_FILES;
        lockFilesOnClose = DEFAULT_LOCK_FILES_ON_CLOSE;
        autoUnlockFilesToOpen = DEFAULT_AUTOUNLOCK_FILES_TO_OPEN;
        autoUnlockFilesToSend = DEFAULT_AUTOUNLOCK_FILES_TO_SEND;
        useDyslexicFont = DEFAULT_USE_DYSLEXIC_FONT;
    }

    //Check if the directory used by the app to store files exists
    private boolean checkAppdataDirectory() {
        File directory = new File(AppConstants.APPDATA_PATH);

        if (directory.exists())
            return true;
        else
            return directory.mkdirs();
    }
}
