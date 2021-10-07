package com.codecool.dungeoncrawl.logic.actors;

public class Position {
    private int x, y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPositionByPosition(Position position){
        this.setX(position.getX());
        this.setY(position.getY());
    }

    public void setPositionByXAndY(int x, int y){ //TODO DELETE????
        this.setX(x);
        this.setY(y);
    }
}
