package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.Random;

public class BigBoy extends Actor{
    private int coolDown;
    String tileName;



    public BigBoy(Cell cell, String tileName) {
        super(cell, tileName);
        this.tileName = tileName;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

   // public String setTileNameToBigBoy2(){ return "bigBoy2";}

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    @Override
    public void move(){//bot
        if(coolDown == 0){
            ArrayList<Cell> targetCells = cell.getPossibleBotMoves();
            System.out.println("moveable tiles count "+targetCells.size());
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
            coolDown = 1;
        }
        else{
            coolDown--;
        }
    }
}
