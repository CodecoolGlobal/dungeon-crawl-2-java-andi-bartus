package com.codecool.dungeoncrawl.popups;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.GameSaver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavePopup {

    private static String fileNameToSave;
    private static Stage popupWindow;

    public static void savePopup(List<String> names, GameSaver gameSaver, ArrayList<GameMap> maps) {

        popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Save the game");

        Label fileNameLabel = new Label("Filename:");
        TextField fileName = new TextField();
        fileName.setMaxWidth(300);
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");

        cancelButton.setOnAction(e -> popupWindow.close());
        saveButton.setOnAction(e -> saveOrConfirm(names, fileName, gameSaver, maps));
        VBox layout = new VBox(10);
        layout.getChildren().addAll(fileNameLabel, fileName, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 500, 500);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private static void saveOrConfirm(List<String> names, TextField fileName, GameSaver gameSaver, ArrayList<GameMap> maps) {
        fileNameToSave = fileName.getText();
        if (names.contains(fileNameToSave)){
            AreYouSurePopup.areYouSurePopup(gameSaver, maps);
        } else {
            popupWindow.close();
            try {
                gameSaver.save(fileNameToSave, maps);
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected static void closePopup() {
        popupWindow.close();
    }

    public static String getFileNameToSave() {
        return fileNameToSave;
    }
}
