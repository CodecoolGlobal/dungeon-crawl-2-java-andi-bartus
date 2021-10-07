package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Gangsta;
import com.codecool.dungeoncrawl.logic.actors.BigBoy;
import com.codecool.dungeoncrawl.logic.actors.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigBoyTest {
    GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    BigBoy bigboy;


    @BeforeEach
    public void createBigBoy(){
        bigboy = new BigBoy(new Position(1, 1), "bigboy");
    }


    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);
        gameMap.getCell(1, 2).setType(CellType.WALL);

        bigboy.move(gameMap);

        assertEquals(1, bigboy.getX());
        assertEquals(1, bigboy.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        bigboy.setPositionbyXandY(0, 0);
        gameMap.getCell(1, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);

        assertEquals(0, bigboy.getX());
        assertEquals(0, bigboy.getY());
    }


}
