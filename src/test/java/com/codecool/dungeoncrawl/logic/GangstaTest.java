package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Gangsta;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GangstaTest {
    GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    Gangsta gangsta;
    Player player = new Player(new Position(4, 4), "player");

    @BeforeEach
    public void createFriendlyNPC(){
        gangsta = new Gangsta(new Position(1, 1), "gangsta");
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);
        gameMap.getCell(1, 2).setType(CellType.WALL);


        gameMap.setCellActorByPosition(new Position(1, 1), gangsta);
        gameMap.setPlayer(player);
        gangsta.move(gameMap);

        assertEquals(1, gangsta.getX());
        assertEquals(1, gangsta.getY());
    }
/*
    @Test
    void cannotMoveOutOfMap() {
        gangsta.setPositionByXAndY(0, 0);
        gameMap.getCell(1, 1).setType(CellType.WALL);
        gameMap.getCell(0, 1).setType(CellType.WALL);
        gameMap.getCell(1, 0).setType(CellType.WALL);

        gameMap.setCellActorByPosition(new Position(1, 1), gangsta);
        gameMap.setPlayer(player);
        gangsta.move(gameMap);

        assertEquals(0, gangsta.getX());
        assertEquals(0, gangsta.getY());
    }
*/

}
