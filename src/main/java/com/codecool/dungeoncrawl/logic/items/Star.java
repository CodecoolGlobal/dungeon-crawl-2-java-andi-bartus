package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Star extends Item{

    public Star(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "star";
    }
}
