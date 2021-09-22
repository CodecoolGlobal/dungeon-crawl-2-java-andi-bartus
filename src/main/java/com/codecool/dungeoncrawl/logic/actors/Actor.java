package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private int health = 10;
    protected boolean canStepOn = false;
    protected int damage = 2;
    String name;




    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public Actor(Cell cell, String tileName) {
        this.cell = cell;
        this.cell.setActor(this);
        this.name = tileName;
    }




    public abstract void move();

    public void move(int dx, int dy){//player
    }



    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }



    public boolean getCanStepOn() {
        return canStepOn;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }
}
