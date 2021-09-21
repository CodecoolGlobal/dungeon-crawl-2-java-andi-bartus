package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    private String tileName = "player";
    public Player(Cell cell) {
        super(cell);
        this.damage = 2;
    }

    public String getTileName() {
        return this.tileName;
    }

    public void setTileNameToTombStone() {
        this.tileName = "tombStone";
    }
}
