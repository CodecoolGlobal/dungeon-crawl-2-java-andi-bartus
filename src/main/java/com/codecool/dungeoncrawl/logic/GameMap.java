package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private ArrayList<Item> items;
    private int ITEMS_OF_FIRST_MAP;

    private ArrayList<Actor> enemies;
    private ArrayList<Gate> gates;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.gates = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public void setPlayerStats(Player originalPlayer) {
        player.setPlayerMapLevel(originalPlayer.getPlayerMapLevel());
        player.setInventory(originalPlayer.getInventory());
        player.setWaterLevel(originalPlayer.getWaterLevel());
        player.setHealth(originalPlayer.getHealth());
        player.setDamage(originalPlayer.getDamage());
        player.setMoney(originalPlayer.getMoney());
    }

    public void setCellActor(int x, int y, Actor actor) {
        this.cells[x][y].setActor(actor);
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void addEnemy(Actor enemy) {
        this.enemies.add(enemy);
    }

    public void addDoor(Gate gate) {
        this.gates.add(gate);
    }

    public void removeDeadEnemies(){
        for (Actor enemy: enemies){
            if (enemy.getHealth() < 0){
                cells[enemy.getX()][enemy.getY()].setActor(null);
                if(cells[enemy.getX()][enemy.getY()].getItem() == null){
                    cells[enemy.getX()][enemy.getY()].setItem(new Coin(cells[enemy.getX()][enemy.getY()], enemy.getCoinValue()));
                }
                enemies.remove(enemy);
                break;
            }
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Cell currentCell){
        cells[currentCell.getX()][currentCell.getY()].setItem(null);

    }

    public void setEnemies(ArrayList<Actor> enemy) {
        this.enemies = enemy;
    }



    public void moveEnemies() {
        for (Actor enemy:enemies){
            enemy.move();
        }
    }

    public ArrayList<Actor> getEnemies() {
        return enemies;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getRefreshStartCoordinates(int visibleSize){
        int playerX = player.getX();
        int playerY = player.getY();

        int side = (visibleSize - 1) / 2;

        int startX = playerX - side;
        int startY = playerY - side;


        if (playerX - side < 0){
            startX = 0;
        }else if (playerX + side >= width-1){
            startX = width - visibleSize;
        }

        if (playerY - side < 0){
            startY = 0;
        }else if (playerY + side >= height-1){
            startY = height - visibleSize;
        }

        return new int[]{startX,startY};
    }
}
