package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.GameMap;
import java.util.ArrayList;
import java.util.Random;

public class Scorpion extends Actor {

    public Scorpion(Position position, String name) {
        super(position, name);
        this.damage = 1;
        this.health = 10;
        this.coinValue = 9;
    }

    public Scorpion(Position position, String name, int health){
        super(position, name);
        this.damage = 1;
        this.health = health;
        this.coinValue = 9;
    }

    public String getTileName() {
        return this.name;
    }

    @Override
    public void move(GameMap map) {
        ArrayList<Position> targetPosition = map.getPossibleBotMoves(position);
        Position nextPosition = new Position(0,0);
        if (targetPosition.size() == 1 &&
                map.getCell(targetPosition.get(0).getX(), targetPosition.get(0).getY()).getActor() == null) {
            Position furtherMove = getFurtherPlayer(position, map);
            Position cornerMove = getCornerPlayer(position, map);

            if (furtherMove != null) {
                nextPosition.setPositionByPosition(furtherMove);
            } else if (cornerMove != null) {
                nextPosition.setPositionByPosition(cornerMove);
            } else {
                nextPosition.setPositionByPosition(targetPosition.get(0));
            }
            map.setCellActorByPosition(position, null);
            map.setCellActorByPosition(nextPosition, this);
            position.setPositionByPosition(nextPosition);
        } else if (targetPosition.size() > 1) {
            Position furtherMove = getFurtherPlayer(position, map);
            Position cornerMove = getCornerPlayer(position, map);

            if (furtherMove != null) {
                nextPosition.setPositionByPosition(furtherMove);
            } else if (cornerMove != null) {
                nextPosition.setPositionByPosition(cornerMove);
            } else {
                Random random = new Random();
                nextPosition.setPositionByPosition(targetPosition.get(random.nextInt(targetPosition.size())));
            }
            map.setCellActorByPosition(position, null);
            map.setCellActorByPosition(nextPosition, this);
            position.setPositionByPosition(nextPosition);
        }
    }

    public Position getFurtherPlayer(Position position, GameMap map) {
        int[][] coordinateDifferences = {
                {2, 0},
                {0, 2},
                {-2, 0},
                {0, -2},
        };

        int newX;
        int newY;
        for (int[] CD : coordinateDifferences) {
            newX = position.getX() + CD[0];
            newY = position.getY() + CD[1];
            if (newX >= 0 && newX < map.getWidth() &&
                    newY >= 0 && newY < map.getHeight()) {

                if (map.getCell(newX, newY).getActor() != null &&
                        map.getCell(newX, newY).getActor().getTileName().equals("player")) {

                    if (map.getCell(newX - CD[0] / 2, newY - CD[1] / 2).getActor() == null &&
                            map.getCell(newX - CD[0] / 2, newY - CD[1] / 2).getType().getCanStepOn()) {

                        return map.getCell(newX - CD[0] / 2, newY - CD[1] / 2).getPosition();
                    }
                }
            }
        }
        return null;
    }

    public Position getCornerPlayer(Position position, GameMap map) {
        int[][] coordinateDifferences = {
                {1, 1},
                {-1, -1},
                {1, -1},
                {-1, 1},
        };

        int newX;
        int newY;
        for (int[] CD : coordinateDifferences) {
            newX = position.getX() + CD[0];
            newY = position.getY() + CD[1];
            if (newX >= 0 && newX < map.getWidth() &&
                    newY >= 0 && newY < map.getHeight()) {

                if (map.getCell(newX, newY).getActor() != null &&
                        map.getCell(newX, newY).getActor().getTileName().equals("player")) {

                    if (map.getCell(position.getX(), newY).getActor() == null
                            && map.getCell(position.getX(), newY).getType().getCanStepOn()) {

                        return map.getCell(position.getX(), newY).getPosition();

                    } else if (map.getCell(newX, position.getY()).getActor() == null
                            && map.getCell(newX, position.getY()).getType().getCanStepOn()) {

                        return map.getCell(newX, position.getY()).getPosition();
                    }
                }
            }
        }
        return null;
    }
}
