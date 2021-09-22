package com.codecool.dungeoncrawl.logic;

public class Door {
    private boolean isOpen;
    private Cell cell;
    private final int newCurrentMap;

    public Door(Cell cell, boolean isOpen, int newCurrentMap) {
        this.cell=cell;
        this.isOpen = isOpen;
        this.newCurrentMap = newCurrentMap;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Cell getCell() {
        return cell;
    }

    public int getNewCurrentMap() {
        return newCurrentMap;
    }
}
