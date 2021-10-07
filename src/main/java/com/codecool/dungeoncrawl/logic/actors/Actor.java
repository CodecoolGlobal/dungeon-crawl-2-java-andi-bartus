package com.codecool.dungeoncrawl.logic.actors;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.GameMap;


public abstract class Actor implements Drawable {
    protected Position position;
    protected int damage;
    protected int coinValue;
    protected int health;
    protected String name;

    public Actor(Position position, String name) {
        this.position = position;
        this.name = name;
    }

    public abstract void move(GameMap map);

    public void move(int dx, int dy) {
    }

    public void setPositionbyXandY(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setPosition(Position position) {
        this.position = position;
    } //TODO DELELTE??

    public Position getPosition() {
        return position;
    }


    public int getHealth() {
        return health;
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
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

    public String getTileName() {
        return name;
    }


}