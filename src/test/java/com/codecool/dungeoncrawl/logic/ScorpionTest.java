package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScorpionTest {
    GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    Scorpion scorpion;

    @BeforeEach
    public void createScorpion(){
        scorpion = new Scorpion(new Position(1, 1), "scorpion");
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);
        gameMap.getCell(1, 2).setType(CellType.WALL);

        scorpion.move(gameMap);

        assertEquals(1, scorpion.getX());
        assertEquals(1, scorpion.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        scorpion.setPositionByXAndY(0, 0);
        gameMap.getCell(1, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);

        assertEquals(0, scorpion.getX());
        assertEquals(0, scorpion.getY());
    }

    @Test
    void playerShealthPointDecreaseFromDemage(){
        Player player = new Player(new Position(1, 2), "player");
        player.setHealth(100000);
        gameMap.setCellActorByPosition(player.getPosition(), player);
        gameMap.setCellActorByPosition(scorpion.getPosition(), scorpion);
        scorpion.move(gameMap);

        assertEquals(99999, player.getHealth());
    }

    @Test
    void cornerPositions(){
        Player player = new Player(new Position(0, 0), "player");

        gameMap.setCellActorByPosition(player.getPosition(), player);
        gameMap.setCellActorByPosition(scorpion.getPosition(), scorpion);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        scorpion.move(gameMap);

        assertEquals(1, scorpion.getX());
        assertEquals(0, scorpion.getY());
    }

    @Test
    void followThePlayer(){
        Player player = new Player(new Position(1,2), "player");

        gameMap.setCellActorByPosition(player.getPosition(), player);
        gameMap.setCellActorByPosition(new Position(1,1), scorpion);

        player.movePlayer(0, 1, gameMap);
        scorpion.move(gameMap);

        assertEquals(1, scorpion.getX());
        assertEquals(2, scorpion.getY());
    }
}
