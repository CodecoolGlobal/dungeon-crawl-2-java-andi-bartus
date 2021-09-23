package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Chick extends Item{

    public Chick(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "chick";
    }
}
