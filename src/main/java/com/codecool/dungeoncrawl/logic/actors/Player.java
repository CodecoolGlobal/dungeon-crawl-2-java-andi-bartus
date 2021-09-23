package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Coin;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Tequila;
import com.codecool.dungeoncrawl.logic.items.Gun;

import java.util.ArrayList;
public class Player extends Actor {
    ArrayList<Item> inventory;
    int waterLevel;
    private static final int MAX_WATER_LEVEL = 20000;
    private String tileName = "player";
    private int playerMapLevel;
    private int money;

    public Player(Cell cell) {
        super(cell);
        this.damage = 50;
        this.setHealth(100000);
        this.inventory = new ArrayList<>();
        this.waterLevel = MAX_WATER_LEVEL;
        this.playerMapLevel = 0;
        this.money = 0;
    }

    public String getTileName() {
        return this.tileName;
    }

    public void setCell(Cell cell) {
        this.cell=cell;
    }

    public void addToInventory(Item item){
        if (item instanceof Tequila){
            ((Tequila) item).useTequila(this);
        }
        else if (item instanceof Coin){
            ((Coin) item).pickupCoin(this);
        }
        else if (item != null){
            if (item instanceof Gun) {
                ((Gun) item).pickUpGun(this);
            }
                inventory.add(item);


        }
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
        this.tileName = "tombStone";
    }

    public void setTileNameToPlayer2() {
        this.tileName = "player2";
    }

     public void setTilenameToPlayer3() {
        this.tileName = "player3";
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
        } else if (!nextCell.getType().getCanStepOn() &&
                (nextCell.getType().equals(CellType.GATE) ||
                nextCell.getType().equals(CellType.GUN_STORE_DOOR) ||
                nextCell.getType().equals(CellType.SALOON_DOOR))
                ){
                    if(canOpenGate(nextCell)) {
                        this.playerMapLevel=nextCell.getGate().getNewCurrentMap();
                    }
        }else if (nextCell.getActor()!=null && !(nextCell.getActor() instanceof FriendlyNPC)) {//hitTargetEnemyBot;
            nextCell.getActor().setHealth(
                    nextCell.getActor().getHealth() - cell.getActor().getDamage()
            );
        }
    }

    private boolean canOpenGate(Cell gateCell) {
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
        return counter == 2;//if currentMap == 0
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public static int getMaxWaterLevel() {
        return MAX_WATER_LEVEL;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public boolean checkMoneyForGun(){
        return this.getMoney() >= Gun.getCost();
    }
}
