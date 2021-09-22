package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Tequila extends Item{
    private final int healAmount = 5;

    public Tequila(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "tequila";
    }

    public void useTequila(Player player) {
        player.setHealth(player.getHealth() + healAmount);
    }
}
