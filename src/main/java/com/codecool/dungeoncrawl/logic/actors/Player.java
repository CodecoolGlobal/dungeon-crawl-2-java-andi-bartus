package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Tequila;

import java.util.ArrayList;

public class Player extends Actor {



    ArrayList<Item> inventory;



    private String tileName = "player";

    public Player(Cell cell) {
        super(cell);
        this.damage = 2;
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return this.tileName;
    }


    public void addToInventory(Item item){
        if (item instanceof Tequila){
            this.setHealth(this.getHealth() + ((Tequila) item).getValue());
        }
        else if (item != null){
            inventory.add(item);
        }
    }

    @Override
    public String toString() {
        StringBuilder inventoryText = new StringBuilder();
        for (Item item : inventory){
            inventoryText.append(Tiles.getTileMap().get(item.getTileName()));
            inventoryText.append(" ");
            inventoryText.append("\n");
        }
        return inventoryText.toString();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }


    public void setTileNameToTombStone() {
        this.tileName = "tombStone";
    }

}
