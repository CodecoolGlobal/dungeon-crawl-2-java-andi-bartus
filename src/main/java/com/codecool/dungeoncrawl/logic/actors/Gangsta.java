package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.Random;

public class Gangsta extends Actor {
    public Gangsta(Cell cell) {
        super(cell);
        this.damage = 5;
        this.health = 30;
    }

    @Override
    public String getTileName() {
        return "gangsta";
    }

    @Override
    public void move(){
        cell.gangstaMovement(this);
    }
}
