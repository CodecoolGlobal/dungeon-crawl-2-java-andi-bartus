package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;


public abstract class Item implements Drawable {
    protected Position position;
    protected String name;

    public Item(Position position, String tileName) {
        this.position = position;
        this.name = tileName;
    }

    public Position getPosition() {
        return position;
    }

    public String getTileName() {
        return name;
    }

    public void useItem(Player player, GameMap map){
        player.getInventory().add(this);
        map.setCellItem(null, position);
    }


}
