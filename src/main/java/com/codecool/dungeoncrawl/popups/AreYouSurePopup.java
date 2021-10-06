package com.codecool.dungeoncrawl.popups;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.GameSaver;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AreYouSurePopup {

    public static void areYouSurePopup(GameSaver gameSaver, ArrayList<GameMap> maps) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(SavePopup.getFileNameToSave()+" already exists!");
        alert.setContentText(SavePopup.getFileNameToSave()+" already exists!\nWould you like to overwrite the already existing state?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> clickedButton = alert.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK ) {
            try {
                gameSaver.save(SavePopup.getFileNameToSave(), maps);
                SavePopup.closePopup();
            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }
        } else {
            alert.close();
        }
    }
}
