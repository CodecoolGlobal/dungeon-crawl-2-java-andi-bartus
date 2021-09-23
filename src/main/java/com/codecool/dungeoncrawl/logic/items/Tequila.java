package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Tequila extends Item{
    private final int healAmount = 5;
    private final int waterAmount = 20;
    String name;

    public Tequila(Cell cell, String tileName) {
        super(cell);
        this.name = tileName;
    }

    public String getTileName() {
        return name;
    }

    public void useTequila(Player player) {
        player.setHealth(player.getHealth() + healAmount);
        player.setWaterLevel(player.getWaterLevel() + waterAmount);
    }


}
