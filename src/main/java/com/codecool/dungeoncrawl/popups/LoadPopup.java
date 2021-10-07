package com.codecool.dungeoncrawl.popups;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class LoadPopup {

    private final GameDatabaseManager dbManager;

    public LoadPopup(GameDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public String popupForLoad() {
        List<String> dialogData = dbManager.getAllNames();
        ChoiceDialog<String> dialog = new ChoiceDialog(dialogData.get(0), dialogData);
        dialog.setHeaderText("Choose where to load save from");
        Optional<String> result = dialog.showAndWait();
        String selected = "";
        if (result.isPresent()) {
            selected = result.get();
        }
        return selected;
    }
}
