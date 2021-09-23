package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;
import java.util.Random;

public class FriendlyNPC extends Actor {
    private static final String[] NPC_NAMES = new String[] {"NPC1", "NPC2", "NPC3", "NPC4", "NPC5", "NPC6"};
    private static final Random random = new Random();
    String tileName;
    public FriendlyNPC(Cell cell, String tileName) {
        super(cell, tileName);
        this.tileName = tileName;
    }

    @Override
    public String getTileName() {
        return tileName;
    }

    public static String getRandomNPCName(){
        return NPC_NAMES[random.nextInt(NPC_NAMES.length)];
    }

    @Override
    public void move(){//bot
        ArrayList<Cell> targetCells = cell.getPossibleNPCMoves();
        Cell nextCell;
        if (targetCells.size() == 1 && targetCells.get(0).getActor() == null){//move to that cell; (does it work?)
            nextCell = targetCells.get(0);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }else if(targetCells.size() > 1){
            //pick random cell to move to (does it work yet?)
            nextCell = targetCells.get(random.nextInt(targetCells.size()));
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }
}
