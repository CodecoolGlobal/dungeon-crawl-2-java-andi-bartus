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
        this.health = 25;
        this.damage = 20;
        this.coinValue = 50;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    @Override
    public void move(){
        if(coolDown == 0){
            ArrayList<Cell> targetCells = cell.getPossibleBotMoves();
            Cell nextCell;
            if (targetCells.size() == 1 && targetCells.get(0).getActor() == null){//move to that cell; (does it work?)
                nextCell = targetCells.get(0);
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }else if(targetCells.size() > 1){
                Random random = new Random();
                nextCell = targetCells.get(random.nextInt(targetCells.size()));
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
            Random random = new Random();
            coolDown = random.nextInt(3);
        }
        else{
            coolDown--;
        }
    }
}
