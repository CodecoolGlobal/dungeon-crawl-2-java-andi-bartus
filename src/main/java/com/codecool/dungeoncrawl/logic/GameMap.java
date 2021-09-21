package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private ArrayList<Actor> enemies;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.enemies = new ArrayList<>();
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
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

    public void removeDeadEnemies(){
        for (Actor enemy: enemies){
            if (enemy.getHealth() < 0){
                cells[enemy.getX()][enemy.getY()].setActor(null);
                enemies.remove(enemy);
                break;
            }
        }
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
}
