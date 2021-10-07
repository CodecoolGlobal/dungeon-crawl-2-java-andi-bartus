package com.codecool.dungeoncrawl.logic.actors;
import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.geometry.Pos;
import java.util.ArrayList;
public class Gangsta extends Actor {
    public Gangsta(Position position, String name) {
        super(position, name);
        this.damage = 10;
        this.coinValue = 25;
        this.health = 30;
    }

    @Override
    public void move(GameMap map){}

    /*@Override
    public void move(GameMap map){
        int gangstaX = this.position.getX();
        int gangstaY = this.position.getY();
        int[][] coordinateDifferences ={
                {1,  0},
                {-1, 0},
                {0,  -1},
                {0, 1},
        };


        boolean hasValidMove = false;
        boolean iDidNotHitHer = true;
        Position possibleMove = new Position(-1,-1);
        Position bestPosition = new Position(-1,-1);
        double moveDistance;
        double bestMoveDistance = Double.MAX_VALUE;
        for (int[] difference:coordinateDifferences) {
            System.out.println("bot ");
            possibleMove = map.getCell(gangstaX + difference[0], gangstaY + difference[1]).getPosition();
            Cell currentCell = map.getCell(possibleMove.getX(), possibleMove.getY());

            if(currentCell.getActor() != null){
                if (currentCell.getActor().getTileName().equals("player3"))
                    System.out.println("hit");
                    map.getPlayer().setHealth(
                        map.getPlayer().getHealth() - map.getCell(gangstaX, gangstaY).getActor().getDamage()
                );
                iDidNotHitHer = false;
            }else if(currentCell.getType().getCanStepOn()){//can step on
                if(currentCell.getActor() == null){//no entity
                    moveDistance = map.getDistanceOfCells(map.getPlayer().getPosition(), possibleMove);
                    if (bestMoveDistance > moveDistance){//better movepos
                        System.out.println("move");
                        bestPosition.setPositionByPosition(possibleMove);

                        System.out.println(map.getPlayer().getX() + " x" + map.getPlayer().getY());
                        System.out.println(bestPosition.getX() + " x" + bestPosition.getY());
                        System.out.println(currentCell.getType().getTileName());
                        System.out.println(currentCell.getType().getCanStepOn());
                        bestMoveDistance = moveDistance;
                        hasValidMove = true;
                    }
                }
            }
        }
        System.out.println(hasValidMove + "   " + iDidNotHitHer);
        if (hasValidMove && iDidNotHitHer){
            map.setCellActorbyPosition(position, null);
            map.setCellActorbyPosition(possibleMove, this);
            position.setPositionByPosition(possibleMove);
        }
    }*/
}
