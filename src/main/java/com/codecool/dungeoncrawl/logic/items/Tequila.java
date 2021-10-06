package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;

public class Tequila extends Item{
    private final int healAmount = 5;
    private final int waterAmount = 15;

    public Tequila(Position position, String name) {
        super(position, name);
    }

    @Override
    public void useItem(Player player) {
        player.setHealth(player.getHealth() + healAmount);
        player.setWaterLevel(player.getWaterLevel() + waterAmount);
    }

}
