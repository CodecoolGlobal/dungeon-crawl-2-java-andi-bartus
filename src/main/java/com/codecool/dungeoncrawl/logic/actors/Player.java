package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.items.Coin;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Tequila;
import com.codecool.dungeoncrawl.logic.items.Gun;

import java.util.ArrayList;
public class Player extends Actor {
    ArrayList<Item> inventory;
    int waterLevel;
    private static final int initialWaterLevel = 20;
    private int playerMapLevel;
    private int money;

    public Player(Position position, String name) {
        super(position, name);
        this.damage = 5;
        this.setHealth(100000);
        this.inventory = new ArrayList<>();
        this.waterLevel = initialWaterLevel;
        this.playerMapLevel = 0 ;
        this.money = 0;
    }


    public int getPlayerMapLevel() {
        return playerMapLevel;
    }

    public void setPlayerMapLevel(int playerMapLevel) {
        this.playerMapLevel = playerMapLevel;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setTileNameToTombStone() {
        this.name = "tombStone";
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public void move(GameMap map){}


    public void movePlayer(int dx, int dy, GameMap map) {
        Cell nextCell = map.getCell(this.position.getX() + dx, this.position.getY() + dy);
        if (nextCell.getType().getCanStepOn() && nextCell.getActor()==null) {
            map.setCellActorbyPosition(position, null);
            map.setCellActorbyPosition(nextCell.getPosition(), this);
            position.setPositionByPosition(nextCell.getPosition());
        } else if (!nextCell.getType().getCanStepOn() &&
                (nextCell.getType().equals(CellType.GATE) ||
                nextCell.getType().equals(CellType.GUN_STORE_DOOR) ||
                nextCell.getType().equals(CellType.SALOON_DOOR))
                ){
                    if(canOpenGate(nextCell)) {
                        this.playerMapLevel=nextCell.getGate().getNewCurrentMap();
                    }
        }else if (nextCell.getActor()!=null && !(nextCell.getActor() instanceof FriendlyNPC)) {
            nextCell.getActor().setHealth(
                    nextCell.getActor().getHealth() - this.getDamage()
            );
        }
    }


    private boolean canOpenGate(Cell gateCell) { //TODO
        int counter = 0;
        for (Item item : inventory) {
            int toMapId = gateCell.getGate().getNewCurrentMap();
            if((toMapId == 1 && playerMapLevel == 0) || (toMapId == 0 && playerMapLevel == 1)){
                if (item.getTileName().equals("hat") || item.getTileName().equals("boots")) {
                    counter++;
                }
            }else if((toMapId == 1 && playerMapLevel == 2) || (toMapId == 2 && playerMapLevel == 1)){
                if (item.getTileName().equals("star")){
                    return true;
                }
            }else if((toMapId == 1 && playerMapLevel == 3) || (toMapId == 3 && playerMapLevel == 1)){
                if (item.getTileName().equals("gun")){
                    return true;
                }
            }
        }
        return counter == 2;
    }


    public boolean canPickUpChick() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getTileName().equals("rose")){
                return true;
            }

        } return false;
    }
}
