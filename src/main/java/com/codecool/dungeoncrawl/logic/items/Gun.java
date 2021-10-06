package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;

public class Gun extends Item{
    private final int damage = 15;
    private static final int cost = 100;

    public Gun(Position position, String name) {
        super(position, name);
    }

    public static int getCost() {
        return cost;
    }

    @Override
    public void useItem(Player player, GameMap map){
        player.setMoney(player.getMoney()-cost);
        player.setDamage(player.getDamage() + damage);
        player.getInventory().add(this);
        map.setCellItem(null, position);

    }
}
