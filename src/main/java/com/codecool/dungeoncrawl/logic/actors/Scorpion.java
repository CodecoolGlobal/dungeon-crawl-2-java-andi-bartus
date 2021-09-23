package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.Random;

public class Scorpion extends Actor{
    private final String tileName = "scorpion";

    public Scorpion(Cell cell){
        super(cell);
        this.damage = 1;
        this.health = 9;
        this.coinValue = 10;
    }

    public String getTileName() {
        return this.tileName;
    }

    @Override
    public void move(){//botn
        ArrayList<Cell> targetCells = cell.getPossibleBotMoves();
        Cell nextCell;
        if (targetCells.size() == 1 && targetCells.get(0).getActor() == null
            ){//move to that cell; (does it work?)
            //surrounding

            Cell furtherMove = cell.getFurtherPlayer(getCell());
            Cell cornerMove = cell.getCornerPlayer(getCell());

            if (furtherMove != null){
                nextCell = furtherMove;
            }else if(cornerMove != null){
                nextCell = cornerMove;
            }else{
                nextCell = targetCells.get(0);
            }

            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if(targetCells.size() > 1){
            //check surroundings
            //
            //pick random cell to move to (does it work yet?)
            Cell furtherMove = cell.getFurtherPlayer(getCell());
            Cell cornerMove = cell.getCornerPlayer(getCell());

            if (furtherMove != null){
                nextCell = furtherMove;
            }else if(cornerMove != null){
                nextCell = cornerMove;
            }else{
                Random random = new Random();
                nextCell = targetCells.get(random.nextInt(targetCells.size()));
            }

            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }


}
