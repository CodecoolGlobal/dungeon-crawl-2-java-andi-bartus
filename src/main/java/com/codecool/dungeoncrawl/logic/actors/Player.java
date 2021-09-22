package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Tequila;

import java.util.ArrayList;
public class Player extends Actor {
    ArrayList<Item> inventory;
    int waterLevel;
    private static final int MAX_WATER_LEVEL = 20;

    private String tileName = "player";

    public Player(Cell cell) {
        super(cell);
        this.damage = 4;
        this.setHealth(20000);
        this.inventory = new ArrayList<>();
        this.waterLevel = MAX_WATER_LEVEL;
    }

    public String getTileName() {
        return this.tileName;
    }


    public void addToInventory(Item item){
        if (item instanceof Tequila){
            ((Tequila) item).useTequila(this);
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

    public void setTileNameToPlayer2() {
        this.tileName = "player2";
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }


    public void move(){}


    public void movePlayer(int dx, int dy) {//player
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().getCanStepOn() && nextCell.getActor()==null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.getActor()!=null) {//hitTargetEnemyBot;
            nextCell.getActor().setHealth(
                    nextCell.getActor().getHealth() - cell.getActor().getDamage()
            );
        }
    }

}
