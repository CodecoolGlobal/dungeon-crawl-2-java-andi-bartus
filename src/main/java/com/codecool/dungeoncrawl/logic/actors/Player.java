package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    private String tileName = "player";
    public Player(Cell cell) {
        super(cell);
        this.damage = 4;
        this.setHealth(20000);
    }

    public String getTileName() {
        return this.tileName;
    }

    public void setTileNameToTombStone() {
        this.tileName = "tombStone";
    }

    public void move(){}


    public void movePlayer(int dx, int dy) {//player
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().getCanStepOn() && nextCell.getActor()==null) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (nextCell.getActor()!=null) {//hitTargetEnemyBot;
            nextCell.getActor().setHealth(
                    nextCell.getActor().getHealth() - cell.getActor().getDamage()
            );
        }
    }

}
