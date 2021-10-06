package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Gun extends Item{
    private final int damage = 15;
    private static final int cost = 100;

    public Gun(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "gun";
    }

    @Override
    public void useItem(Player player){
        player.setMoney(player.getMoney()-cost);
        player.setDamage(player.getDamage() + damage);
        player.getInventory().add(this);
    }

    public static int getCost() {
        return cost;
    }
}
