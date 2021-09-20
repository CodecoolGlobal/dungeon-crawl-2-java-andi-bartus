package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;

import java.util.ArrayList;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;
    private ArrayList<Skeleton> skeletons;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.skeletons = new ArrayList<>();
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

    public void addSkeleton(Skeleton skeleton) {
        this.skeletons.add(skeleton);
    }

    public void removeDeadSkeletons(){
        for (Skeleton skeleton:skeletons){
            if (skeleton.getHealth() < 0){
                cells[skeleton.getX()][skeleton.getY()].setActor(null);
                skeletons.remove(skeleton);
                break;
            }
        }
    }

    public void setSkeletons(ArrayList<Skeleton> skeletons) {
        this.skeletons = skeletons;
    }

    public void moveSkeletons() {
        for (Skeleton skeleton:skeletons){
            skeleton.move();
        }
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
