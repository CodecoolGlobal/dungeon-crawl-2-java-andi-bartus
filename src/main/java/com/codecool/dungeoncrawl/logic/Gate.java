package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Position;

public class Gate {


    private Position position;
    private final int newCurrentMap;


    private CellType type;

    public CellType getType() {
        return type;
    }

    public Gate(Position position, int newCurrentMap, CellType cellType) {
        this.position=position;
        this.newCurrentMap = newCurrentMap;
        this.type = cellType;
    }

    public Position getPosition() {
        return position;
    } //TODO DELETE????

    public int getNewCurrentMap() {
        return newCurrentMap;
    }
}
