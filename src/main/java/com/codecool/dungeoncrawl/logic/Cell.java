package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private Door door;


    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;

    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public Door getDoor() {
        return door;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public void addDoor(Door door) {
        this.door = door;
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public ArrayList<Cell> getPossibleBotMoves(){
        int[][] coordinateDifferences ={
                {0,  1},
                {0, -1},
                {1,  0},
                {-1, 0},
        };
        Cell neighbor;
        ArrayList<Cell> stepAbleCells = new ArrayList<>();
        for (int[] difference:coordinateDifferences) {
            neighbor = gameMap.getCell(x+difference[0], y+difference[1]);
            if (neighbor.getActor()!= null && (neighbor.getActor().getTileName().equals("player")
                || neighbor.getActor().getTileName().equals("player2"))){

                neighbor.getActor().setHealth(
                        neighbor.getActor().getHealth() - gameMap.getCell(x,y).getActor().getDamage()
                );

                ArrayList<Cell> player = new ArrayList<>();//no movement (check length == 0 when returned
                player.add(neighbor);
                return player;
            }else if (neighbor.getType().getCanStepOn() && neighbor.getActor()==null){
                stepAbleCells.add(neighbor);
            }
        }
        return stepAbleCells;
    }


    public Cell getCornerPlayer(Cell mob){
        int[][] coordinateDifferences ={
                {1,  1},
                {-1, -1},
                {1,  -1},
                {-1, 1},
        };

        int newX;
        int newY;
        Cell moveTo;
        for (int[] CD: coordinateDifferences) {
            newX = mob.getX() + CD[0];
            newY = mob.getY() + CD[1];
            if (newX >=0 && newX < gameMap.getWidth() &&
                newY >=0 && newY < gameMap.getHeight()) {

                if (gameMap.getCell(newX, newY).getActor() != null &&
                    gameMap.getCell(newX, newY).getActor().getTileName().equals("player")) {

                    if (gameMap.getCell(mob.getX(), newY).getActor() == null
                        && gameMap.getCell(mob.getX(), newY).getType().getCanStepOn()) {

                        moveTo = gameMap.getCell(mob.getX(), newY);
                        return moveTo;
                    } else if (gameMap.getCell(newX, mob.getY()).getActor() == null
                               && gameMap.getCell(newX, mob.getY()).getType().getCanStepOn()) {

                        moveTo = gameMap.getCell(newX, mob.getY());
                        return moveTo;
                    }
                }
            }
        }
        return null;
    }

    public Cell getFurtherPlayer(Cell mob){
        int[][] coordinateDifferences ={
                {2,  0},
                {0, 2},
                {-2,  0},
                {0, -2},
        };

        int newX;
        int newY;
        Cell moveTo;
        for (int[] CD: coordinateDifferences) {
            newX = mob.getX() + CD[0];
            newY = mob.getY() + CD[1];
            if (newX >=0 && newX < gameMap.getWidth() &&
                newY >=0 && newY < gameMap.getHeight()){

                if (gameMap.getCell(newX, newY).getActor() != null &&
                        gameMap.getCell(newX, newY).getActor().getTileName().equals("player")){

                    if(gameMap.getCell(newX - CD[0] / 2, newY - CD[1] / 2).getActor() == null &&
                       gameMap.getCell(newX - CD[0] / 2, newY - CD[1] / 2).getType().getCanStepOn()){

                        moveTo = gameMap.getCell(newX - CD[0] / 2, newY - CD[1] / 2);
                        return moveTo;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
