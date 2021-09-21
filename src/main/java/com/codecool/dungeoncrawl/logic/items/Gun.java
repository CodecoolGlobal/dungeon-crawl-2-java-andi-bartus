package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Gun extends Item{

    public Gun(Cell cell) {
        super(cell);
    }


    public String getTileName() {
        return "gun";
    }
}
