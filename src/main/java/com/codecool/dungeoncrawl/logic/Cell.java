package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Gangsta;
import com.codecool.dungeoncrawl.logic.actors.Position;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private Gate gate;


    private Position position = new Position(0, 0);

    Cell(int x, int y, CellType type) {
        this.position.setX(x);
        this.position.setY(y);
        this.type = type;
    }
    Cell(Position position, CellType type) {
        this.position = position;
        this.type = type;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Gate getGate() {
        return gate;
    }

    public void addDoor(Gate gate) {
        this.gate = gate;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Position getPosition() { return position; }



}
