package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameSaver {

    private final GameDatabaseManager dbManager;

    public GameSaver(GameDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void save(String saveName, ArrayList<GameMap> maps) throws SQLException, IOException {
        String json = new Gson().toJson(maps);
        List<String> names = dbManager.getAllNames();
        if (names.contains(saveName)) {
            dbManager.updateJSON(saveName, json);
        } else {
            dbManager.saveJSON(saveName, json);
        }
        writeSaveToFile(saveName, json);
    }

    public void writeSaveToFile(String saveName, String saveContent) throws IOException {
        try {
            FileWriter writer = new FileWriter(String.format("src/main/resources/saves/%s.json", saveName));
            writer.write(saveContent);
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void exportSave(String saveName, File location, ArrayList<GameMap> maps) throws IOException {
        try {
            String json = new Gson().toJson(maps);
            FileWriter writer = new FileWriter(location+".json");
            writer.write(json);
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
