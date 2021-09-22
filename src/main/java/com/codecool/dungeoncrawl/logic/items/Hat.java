package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Hat extends Item{
    public Hat(Cell cell) {
        super(cell);
    }

     public String getTileName() { return "hat";
    }
}
