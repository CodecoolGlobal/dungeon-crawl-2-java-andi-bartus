package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;
import java.util.Random;

public abstract class Actor implements Drawable {
    protected Cell cell;
    protected int health;
    protected boolean canStepOn = false;
    protected int damage;
    protected int coinValue;

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

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCoinValue() {
        return coinValue;
    }
}
