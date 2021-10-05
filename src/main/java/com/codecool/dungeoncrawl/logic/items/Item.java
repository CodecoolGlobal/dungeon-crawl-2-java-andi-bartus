package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Item implements Drawable {
    transient protected Cell cell;
    String name;

    public Item(Cell cell, String tileName) {
        this.cell = cell;
        this.name = tileName;
    }

    public Item(Cell cell) {
        this.cell = cell;
        if (cell != null) {
            this.cell.setItem(this);
        }
    }


    public Cell getCell() {
        return cell;
    }
}
