package com.doppelgunner.doppeleater.util;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by robertoguazon on 14/01/2017.
 */
public class Chooser {

    private static FileChooser fileChooser;

    public static FileChooser getFileChooser() {
        if (fileChooser == null) fileChooser = new FileChooser();
        return fileChooser;
    }

    public static File chooseFile(String title, Stage mainStage, FileChooser.ExtensionFilter ... extensionFilters) {
        FileChooser fileChooser = getFileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        return selectedFile;
    }

    public static File chooseImage(Stage mainStage) {
        return chooseFile("Choose an image", mainStage, new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));
    }

}
