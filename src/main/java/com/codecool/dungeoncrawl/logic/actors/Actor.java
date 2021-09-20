package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.ArrayList;
import java.util.Random;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    protected boolean canStepOn = false;
    protected int damage = 2;



    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(){//bot
        ArrayList<Cell> targetCells = cell.getPossibleBotMoves();
        Cell nextCell;
        if (targetCells.size() == 1){//move to that cell; (does it work?)
            nextCell = targetCells.get(0);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }else if(targetCells.size() > 1){
            //pick random cell to move to (does it work yet?)
            Random random = new Random();
            nextCell = targetCells.get(random.nextInt(targetCells.size()));
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void move(int dx, int dy) {//player
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
