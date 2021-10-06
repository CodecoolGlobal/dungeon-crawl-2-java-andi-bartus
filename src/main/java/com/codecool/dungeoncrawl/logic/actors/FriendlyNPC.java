package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;

import java.util.ArrayList;
import java.util.Random;

public class FriendlyNPC extends Actor {
    private static final String[] NPC_NAMES = new String[] {"NPC1", "NPC2", "NPC3", "NPC4", "NPC5", "NPC6"};
    private static final Random random = new Random();

    public FriendlyNPC(Position position, String name) {
        super(position, name);
     }

    @Override
    public String getTileName() {
        return name;
    }

    public static String getRandomNPCName(){
        return NPC_NAMES[random.nextInt(NPC_NAMES.length)];
    }

    @Override
    public void move(GameMap map){
        ArrayList<Position> targetPositions = map.getPossibleNPCMoves(position);
        Position nextPosition;
        if (targetPositions.size() == 1 &&
            map.getCell(targetPositions.get(0).getX(), targetPositions.get(0).getY()).getActor() == null){
            nextPosition = targetPositions.get(0);
            map.setCellActor(this.position.getX(), this.position.getY(), null);
            map.setCellActor(nextPosition.getX(), nextPosition.getY(), this);
            position.setPositionByPosition(nextPosition);
        }else if(targetPositions.size() > 1){
            Random random = new Random();
            nextPosition = targetPositions.get(random.nextInt(targetPositions.size()));
            map.setCellActor(this.position.getX(), this.position.getY(), null);
            map.setCellActor(nextPosition.getX(), nextPosition.getY(), this);
        }
    }
}
