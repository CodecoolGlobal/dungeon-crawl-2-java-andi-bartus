package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.List;

public class GameSaver {

    private final GameDatabaseManager dbManager;

    public GameSaver(GameDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void save(String saveName) throws SQLException {
        JsonObject new_save = new JsonObject(); // TODO bens
        List<String> names = dbManager.getAllNames();
        if (names.contains(saveName)) {
            //ToDo update DB with new save
        } else {
            dbManager.saveJSON(saveName, new_save.toString());
        }

    }
}
