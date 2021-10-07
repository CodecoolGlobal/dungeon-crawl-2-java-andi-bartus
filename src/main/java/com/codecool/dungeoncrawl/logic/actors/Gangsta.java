package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.geometry.Pos;

public class Gangsta extends Actor {
    public Gangsta(Position position, String name) {
        super(position, name);
        this.damage = 10;
        this.coinValue = 25;
        this.health = 30;
    }

    @Override
    public void move(GameMap map){
        int gangstaX = position.getX();
        int gangstaY = position.getY();
        int[][] coordinateDifferences ={
                {1,  0},
                {-1, 0},
                {0,  -1},
                {0, 1},
        };

        Position possibleMove = new Position(-1,-1);
        double moveDistance;
        double bestMoveDistance = Double.MAX_VALUE;
        Position bestPosition = new Position(-1,-1);
        boolean iDidNotHitHer = true;
        System.out.println(map.getPlayer().getX() + "  "+map.getPlayer().getY());
        for (int[] difference:coordinateDifferences) {

            possibleMove = map.getCell(gangstaX + difference[0], gangstaY + difference[1]).getPosition();

            if(map.getCell(possibleMove.getX(), possibleMove.getY()).getActor() != null){
                if (map.getCell(possibleMove.getX(), possibleMove.getY()).getActor().getTileName().equals("player3"))
                map.getPlayer().setHealth(
                        map.getPlayer().getHealth() - map.getCell(gangstaX, gangstaY).getActor().getDamage()
                );
                iDidNotHitHer = false;
            }else if(map.getCell(possibleMove.getX(), possibleMove.getY()).getType().getCanStepOn()){//can step on
                if(map.getCell(possibleMove.getX(), possibleMove.getY()).getActor() == null){//no entity
                    moveDistance = map.getDistanceOfCells(map.getPlayer().getPosition(), possibleMove);
                    if (bestMoveDistance > moveDistance){//better movepos
                        bestPosition.setPositionByPosition(possibleMove);
                        bestMoveDistance = moveDistance;
                    }
                }
            }
        }
        if (bestPosition.getX() != -1 &&
                bestPosition.getY() != -1 &&
                iDidNotHitHer
        ){
            map.setCellActorbyPosition(position, null);
            map.setCellActorbyPosition(possibleMove, this);
            position.setPositionByPosition(possibleMove);
        }
    }
}
