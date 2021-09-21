package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;



    private Item item;


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

    public void setItem(Item item) {
        this.item = item;
    }


    public void setActor(Actor actor) {
        this.actor = actor;
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
            if (neighbor.getActor()!= null && neighbor.getActor().getTileName().equals("player")){
                neighbor.getActor().setHealth(
                        neighbor.getActor().getHealth() - gameMap.getCell(x,y).getActor().getDamage()
                );//hit player
                return new ArrayList<>();//no movement (check length == 0 when returned
                                         // if true : pass movement, else pick rand if length>2)
            }else if (neighbor.getType().getCanStepOn()){
                stepAbleCells.add(neighbor);
            }
        }
        return stepAbleCells;
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
