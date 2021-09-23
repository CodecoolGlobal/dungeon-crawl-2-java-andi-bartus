package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Rose extends Item{
    public Rose(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "rose";
    }
}
