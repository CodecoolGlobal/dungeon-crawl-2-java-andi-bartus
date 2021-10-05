package com.codecool.dungeoncrawl.logic;

public class Gate {
    transient private Cell cell;
    private final int newCurrentMap;

    public Gate(Cell cell, int newCurrentMap) {
        this.cell=cell;
        this.newCurrentMap = newCurrentMap;
    }

    public Cell getCell() {
        return cell;
    }

    public int getNewCurrentMap() {
        return newCurrentMap;
    }
}
