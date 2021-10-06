package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Position;

public class Gate {


    private Position position;
    private final int newCurrentMap;

    public Gate(Position position, int newCurrentMap) {
        this.position=position;
        this.newCurrentMap = newCurrentMap;
    }

    public Position getPosition() {
        return position;
    }

    public int getNewCurrentMap() {
        return newCurrentMap;
    }
}
