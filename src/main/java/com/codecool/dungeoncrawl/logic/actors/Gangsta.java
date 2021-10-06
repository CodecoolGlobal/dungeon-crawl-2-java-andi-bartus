package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

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
        double baseDistance = map.getDistanceOfCells(map.getPlayer().getPosition(), position);
        int[][] coordinateDifferences ={
                {1,  0},
                {-1, 0},
                {0,  -1},
                {0, 1},
        };

        Cell possibleMove;
        double possibleMoveDistance;
        for (int[] difference:coordinateDifferences) {
            possibleMove = map.getCell(gangstaX + difference[0], gangstaY + difference[1]);

            if (possibleMove.getActor() != null && possibleMove.getActor().getTileName().equals("player3")){
                map.getPlayer().setHealth(
                        map.getPlayer().getHealth() - map.getCell(gangstaX, gangstaY).getActor().getDamage()
                );
            }else if (possibleMove.getType().getCanStepOn() && possibleMove.getActor()==null){
                possibleMoveDistance = map.getDistanceOfCells(map.getPlayer().getPosition(), position);
                if (baseDistance >= possibleMoveDistance){
                    map.setCellActorbyPosition(position, null);
                    map.setCellActorbyPosition(possibleMove.getPosition(), this);
                    position.setPositionByPosition(possibleMove.getPosition());
                    break;
                }
            }
        }
    }
}
