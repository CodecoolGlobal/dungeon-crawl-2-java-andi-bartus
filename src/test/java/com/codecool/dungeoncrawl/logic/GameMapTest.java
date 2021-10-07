package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.BigBoy;
import com.codecool.dungeoncrawl.logic.actors.Gangsta;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;
import com.codecool.dungeoncrawl.logic.items.Coin;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Star;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    GameMap map;

    @BeforeEach
    public void createMap(){
        map =  new GameMap(10, 10, CellType.FLOOR);
    }

    @Test
    void removeDeadEnemies(){
        Gangsta gangsta = new Gangsta(new Position(1, 1), "gangsta");
        map.addEnemy(gangsta);
        gangsta.setHealth(-1);

        map.removeDeadEnemies();
        assertNull(map.getCell(1, 1).getActor());
    }

    @Test
    void setPlayersStat(){
        Star testItem = new Star(new Position(2, 2), "star");
        ArrayList<Item> testInventory = new ArrayList<>();
        testInventory.add(testItem);

        Player newPlayer = new Player(new Position(1, 1), "player");
        Player oldPlayer = new Player(new Position(1, 1), "player");
        oldPlayer.setHealth(1);
        oldPlayer.setMoney(5);
        oldPlayer.setPlayerMapLevel(1);
        oldPlayer.setDamage(3);
        oldPlayer.getInventory().add(testItem);
        oldPlayer.setWaterLevel(1);
        map.setPlayer(newPlayer);
        map.setPlayerStats(oldPlayer);

        assertEquals(newPlayer.getHealth(), 1);
        assertEquals(newPlayer.getMoney(), 5);
        assertEquals(newPlayer.getPlayerMapLevel(), 1);
        assertEquals(newPlayer.getDamage(), 3);
        assertEquals(newPlayer.getInventory(), testInventory);
        assertEquals(newPlayer.getWaterLevel(), 1);
    }

    @Test
    void deadEnemyChangesToMoney(){
        Gangsta gangsta = new Gangsta(new Position(1, 1), "gangsta");
        map.addEnemy(gangsta);
        gangsta.setHealth(-1);

        map.removeDeadEnemies();
        Coin coin = (Coin) map.getCell(1, 1).getItem();
        int value = coin.getValue();

        assertEquals(25, value);
    }

    @Test
    void addItemToList(){
        Star testStar = new Star(new Position(1, 1), "star");

        map.addItem(testStar, testStar.getPosition());

        assertEquals(testStar, map.getCell(1, 1).getItem());
    }

    @Test
    void moveEnemies(){
        BigBoy bigBoy = new BigBoy(new Position(1, 1), "bigBoy");
        BigBoy bigBoy2 = new BigBoy(new Position(4, 4), "bigBoy2");
        BigBoy bigBooy3 = new BigBoy(new Position(6, 6), "bigBoy3");
        map.addEnemy(bigBoy);
        map.addEnemy(bigBoy2);
        map.addEnemy(bigBooy3);

        map.moveEnemies();

        assertNotEquals(map.getCell(1, 1).getActor(), bigBoy);
        assertNotEquals(map.getCell(4, 4).getActor(), bigBoy2);
        assertNotEquals(map.getCell(6, 6).getActor(), bigBooy3);
    }
}
