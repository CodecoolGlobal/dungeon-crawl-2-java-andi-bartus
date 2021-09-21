package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Tequila extends Item{
    private final int value = 5;

    public Tequila(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "tequila";
    }

    public int getValue() {
        return value;
    }
}
