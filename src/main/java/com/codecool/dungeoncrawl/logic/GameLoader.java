package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import java.io.File;

public class GameLoader {

    private final GameDatabaseManager dbManager;

    public GameLoader(GameDatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void loadGame(File file) {
        //Todo load the game
        System.out.println(file);
    }
}
