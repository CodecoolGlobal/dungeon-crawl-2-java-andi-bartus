package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Player;

public class Gun extends Item{
    private final int damage = 10;

    public Gun(Cell cell) {
        super(cell);
    }


    public String getTileName() {
        return "gun";
    }

    public int getDamage() {
        return damage;
    }

    public void pickUpGun(Player player){
        player.setDamage(player.getDamage() + damage);
    }
}
