package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Player extends Actor {
    @Override
    public String toString() {
        return "Player{" +
                "inventory=" + inventory +
                '}';
    }

    ArrayList<Item> inventory;


    public Player(Cell cell) {
        super(cell);
        this.damage = 2;
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item){
        if (item != null){inventory.add(item);};
    }

}
