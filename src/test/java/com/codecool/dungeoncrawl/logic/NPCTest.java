package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.BigBoy;
import com.codecool.dungeoncrawl.logic.actors.FriendlyNPC;
import com.codecool.dungeoncrawl.logic.actors.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NPCTest {

    GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    FriendlyNPC grandma;


    @BeforeEach
    public void createFriendlyNPC(){
        grandma = new FriendlyNPC(new Position(1, 1), FriendlyNPC.getRandomNPCName());
    }


    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);
        gameMap.getCell(1, 2).setType(CellType.WALL);

        grandma.move(gameMap);

        assertEquals(1, grandma.getX());
        assertEquals(1, grandma.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        grandma.setPositionbyXandY(0, 0);
        gameMap.getCell(1, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);

        assertEquals(0, grandma.getX());
        assertEquals(0, grandma.getY());
    }


}
