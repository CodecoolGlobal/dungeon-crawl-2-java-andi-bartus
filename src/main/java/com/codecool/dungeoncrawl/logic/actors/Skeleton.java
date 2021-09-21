package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.Random;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.damage = 3;
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }

    @Override
    public void move(){//bot
        ArrayList<Cell> targetCells = cell.getPossibleBotMoves();
        Cell nextCell;
        if (targetCells.size() == 1 && targetCells.get(0).getActor() == null){//move to that cell; (does it work?)
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
}
