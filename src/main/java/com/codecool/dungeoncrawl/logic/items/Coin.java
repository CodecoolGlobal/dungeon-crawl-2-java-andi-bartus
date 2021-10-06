package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Coin extends Item{
    private int value;

    public Coin(Cell cell, int amount) {
        super(cell);
        this.value = amount;
    }

    @Override
    public String getTileName() {
        return "coin";
    }

    @Override
    public void useItem(Player player){
        player.setMoney(player.getMoney() + this.value);
    }
}
