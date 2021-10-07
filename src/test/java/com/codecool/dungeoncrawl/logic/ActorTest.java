package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class PlayerTest {
    GameMap gameMap = new GameMap(10, 10, CellType.FLOOR);
    Player player;

    @BeforeEach
    public void createPlayer(){
        player = new Player(new Position(1, 1), "player");
    }


    @Test
    void moveUpdatesCells() {
        player.movePlayer(1, 0, gameMap);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        player.movePlayer(1, 0, gameMap);

        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        player.movePlayer(1, 0, gameMap);

        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoGangsta() {
        Gangsta gangsta = new Gangsta(new Position(1, 2), "gangsta");
        gameMap.setCellActorbyPosition(new Position(1, 1), player);
        gameMap.setCellActorbyPosition(new Position(1, 2), gangsta);
        player.movePlayer(0, 1, gameMap);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(1, gangsta.getX());
        assertEquals(2, gangsta.getY());

        assertEquals(gangsta, gameMap.getCell(1, 2).getActor());
    }

    @Test
    void cannotMoveIntoScorpion() {
        Scorpion scorpion = new Scorpion(new Position(1, 2), "scorpion");
        gameMap.setCellActorbyPosition(new Position(1, 1), player);
        gameMap.setCellActorbyPosition(new Position(1, 2), scorpion);
        player.movePlayer(0, 1, gameMap);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(1, scorpion.getX());
        assertEquals(2, scorpion.getY());

        assertEquals(scorpion, gameMap.getCell(1, 2).getActor());
    }




    @Test
    void addGunToInventory(){
        Gun gun = new Gun(new Position(1, 2), "gun");
        gun.useItem(player, gameMap);
        ArrayList<Item> inventory = new ArrayList<>();
        inventory.add(gun);
        assertEquals(inventory, player.getInventory());
    }

    @Test
    void addItemToInventory(){
        Boots boots = new Boots(new Position(1, 2), "boots");
        Chick chick = new Chick(new Position(1, 3), "chick");
        Gun gun = new Gun(new Position(1, 5), "gun");
        Hat hat = new Hat(new Position(1, 6), "hat");
        Rose rose = new Rose(new Position(1, 7), "rose");
        Star star = new Star(new Position(1, 8), "star");

        ArrayList<Item> testInventory = new ArrayList<>();
        testInventory.add(boots);
        testInventory.add(chick);
        testInventory.add(gun);
        testInventory.add(hat);
        testInventory.add(rose);
        testInventory.add(star);

        boots.useItem(player, gameMap);
        chick.useItem(player, gameMap);
        gun.useItem(player, gameMap);
        hat.useItem(player, gameMap);
        rose.useItem(player, gameMap);
        star.useItem(player, gameMap);
        assertEquals(testInventory, player.getInventory());
    }

    @Test
    void drinkTequilaIncreaseHealtandWaterlevel(){
        Tequila tequila = new Tequila(new Position(1, 2), "tequila");
        tequila.useItem(player, gameMap);

        assertEquals(player.getWaterLevel(), 35);
        assertEquals(player.getHealth(), 100005);
    }


    @Test
    void pickUpCoinIncreaseMoneyValue(){
        Coin coin = new Coin(new Position(1, 2), 10);
        coin.useItem(player, gameMap);

        assertEquals(player.getMoney(), 10);
    }

    @Test
    void canPickUpChickWithRose(){
        Rose rose = new Rose(new Position(1, 2), "rose");
        rose.useItem(player, gameMap);
        assertTrue(player.canPickUpChick());

    }

    @Test
    void cantPickUpChickWithoutRose(){
        assertFalse(player.canPickUpChick());
    }


    @Test
    void canPlayerMoveFromMap0ToMap1(){
        Gate gate = new Gate(new Position(1, 2), 1);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);

        player.getInventory().add(new Hat(new Position(2,2), "hat"));
        player.getInventory().add(new Boots(new Position(2, 3), "boots"));
        player.movePlayer(0, 1, gameMap);
        assertEquals(1, player.getPlayerMapLevel());

    }

    @Test
    void canPlayerMoveFromMap0ToMap1WitoutHat(){
        Gate gate = new Gate(new Position(1, 2), 1);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);
        player.getInventory().add(new Boots(new Position(2, 3), "boots"));
        player.movePlayer(0, 1, gameMap);
        assertEquals(0, player.getPlayerMapLevel());


    }

    @Test
    void canPlayerMoveFromMap0ToMap1WitoutBoots(){
        Gate gate = new Gate(new Position(1, 2), 1);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);
        player.getInventory().add(new Hat(new Position(2, 3), "hat"));
        player.movePlayer(0, 1, gameMap);
        assertEquals(0, player.getPlayerMapLevel());
    }

    @Test
    void canPlayerMoveFromMap0ToMap1WitEmptyInventory(){
        Gate gate = new Gate(new Position(1, 2), 1);
        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);
        player.movePlayer(0, 1, gameMap);
        assertEquals(0, player.getPlayerMapLevel());
    }

    @Test
    void canPlayerMoveFromMap1ToMap2(){ //from town to gunstore
        player.setPlayerMapLevel(1);
        Gate gate = new Gate(new Position(1, 2), 2);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);

        player.getInventory().add(new Star(new Position(2,2), "star"));
        player.movePlayer(0, 1, gameMap);
        assertEquals(2, player.getPlayerMapLevel());

    }

    @Test
    void canPlayerMoveFromMap1ToMap2WithoutStar(){ //from town to gunstore
        player.setPlayerMapLevel(1);
        Gate gate = new Gate(new Position(1, 2), 2);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);


        player.movePlayer(0, 1, gameMap);
        assertEquals(1, player.getPlayerMapLevel());

    }

    @Test
    void canPlayerMoveFromMap1ToMap3(){ //from town to saloon
        player.setPlayerMapLevel(1);
        Gate gate = new Gate(new Position(1, 2), 3);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);

        player.getInventory().add(new Star(new Position(2,2), "star"));
        player.getInventory().add(new Gun(new Position(2, 3), "gun"));
        player.movePlayer(0, 1, gameMap);
        assertEquals(3, player.getPlayerMapLevel());

    }

    @Test
    void canPlayerMoveFromMap1ToMap3WithoutGun(){ //from town to saloon
        player.setPlayerMapLevel(1);
        Gate gate = new Gate(new Position(1, 2), 3);

        gameMap.addDoor(gate);
        gameMap.getCell(1, 2).addDoor(gate);
        gameMap.getCell(1, 2).setType(CellType.GATE);

        player.getInventory().add(new Star(new Position(2,2), "star"));

        player.movePlayer(0, 1, gameMap);
        assertEquals(1, player.getPlayerMapLevel());

    }

    @Test
    void canPlayerGotHurtFromNPSs(){
        FriendlyNPC grandma = new FriendlyNPC(new Position(1, 2), "NPC1");
        player.movePlayer(0, 1, gameMap);
        assertEquals(100000, player.getHealth());

    }

    @Test
    void enemyShealthPointDecreasefromDamage(){
        Gangsta gangsta = new Gangsta(new Position(1, 2), "gangsta");

        gameMap.setCellActorbyPosition(new Position(1, 1), player);
        gameMap.setCellActorbyPosition(new Position(1, 2), gangsta);
        player.movePlayer(0, 1, gameMap);

        assertEquals(25, gangsta.getHealth());

    }










 /*   @Test
    void scorpioCannotMoveIntoPlayer() {
        Player player = new Player(new Position(6, 4), "player");
        Scorpion gangsta = new Scorpion(new Position(7, 4), "scorpion"); //7, 5
        gameMap.setCellActorbyPosition(new Position(1, 1), player);
        gameMap.setCellActorbyPosition(new Position(1, 2), gangsta);
        player.movePlayer(0, 1, gameMap);
        gangsta.move(gameMap);
        System.out.println(gangsta.getX());
        System.out.println(gangsta.getY());
        System.out.println(player.getX());
        System.out.println(player.getY());
        assertEquals(6, player.getX());
        assertEquals(5, player.getY());
        assertEquals(7, gangsta.getX());
        assertEquals(4, gangsta.getY());


        assertEquals(gangsta, gameMap.getCell(7, 4).getActor());
    }*/


    public static class EnemyTest {
        GameMap map = new GameMap(3, 3, CellType.FLOOR);
    }
}
