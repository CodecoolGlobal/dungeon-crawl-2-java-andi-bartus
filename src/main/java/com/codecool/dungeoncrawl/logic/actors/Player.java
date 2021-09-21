package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Tequila;

import java.util.ArrayList;
public class Player extends Actor {
    ArrayList<Item> inventory;
    int waterLevel;
    int MAX_WATER_LEVEL = 20;

    private String tileName = "player";

    public Player(Cell cell) {
        super(cell);
        this.damage = 2;
        this.inventory = new ArrayList<>();
        this.waterLevel = MAX_WATER_LEVEL;
    }

    public String getTileName() {
        return this.tileName;
    }


    public void addToInventory(Item item){
        if (item instanceof Tequila){
            this.setHealth(this.getHealth() + ((Tequila) item).getValue());
            this.setWaterLevel(MAX_WATER_LEVEL);
        }
        else if (item != null){
            inventory.add(item);
        }
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }


    public void setTileNameToTombStone() {
        this.tileName = "tombStone";
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

}
