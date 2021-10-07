package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;

public class Coin extends Item{
    private int value;

    public Coin(Position position, int amount) {
        super(position, "coin");
        this.value = amount;
    }

    @Override
    public String getTileName() {
        return "coin";
    }

    @Override
    public void useItem(Player player, GameMap map){
        player.setMoney(player.getMoney() + this.value);
        map.setCellItem(null, position);
    }

    public int getValue() {
        return value;
    }
}
