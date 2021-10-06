package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class BigBoy extends Actor{
    private int coolDown;

    public BigBoy(Position position, String name) {
        super(position, name);
        this.health = 25;
        this.damage = 20;
        this.coinValue = 50;
    }

    @Override
    public void move(GameMap map) {
        if (coolDown == 0) {
            ArrayList<Position> targetCells = map.getPossibleBotMoves(position);
            Position nextPosition;
            if (targetCells.size() == 1 &&
                    map.getCell(targetCells.get(0).getX(), targetCells.get(0).getY()).getActor() == null) {//move to that cell; (does it work?)
                nextPosition = targetCells.get(0);
                map.setCellActor(this.position.getX(), this.position.getY(), null);
                map.setCellActor(nextPosition.getX(), nextPosition.getY(), this);
                position.setPositionByPosition(nextPosition);
            } else if (targetCells.size() > 1) {
                Random random = new Random();
                nextPosition = targetCells.get(random.nextInt(targetCells.size()));
                map.setCellActor(this.position.getX(), this.position.getY(), null);
                map.setCellActor(nextPosition.getX(), nextPosition.getY(), this);
                position.setPositionByPosition(nextPosition);
            }
            Random random = new Random();
            coolDown = random.nextInt(3);
        } else {
            coolDown--;
        }






  /*  @Override
    public String getTileName() {
        return name;
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
    }*/
    }
}
