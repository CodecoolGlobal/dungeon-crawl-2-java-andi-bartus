package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.ArrayList;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class GameMap {
    private final int width;
    private final int height;
    private Cell[][] cells;
    private ArrayList<Item> items;

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Actor> getEnemies() {
        return enemies;
    }

    public ArrayList<Gate> getGates() {
        return gates;
    }
    public void setCellGateByPosition(Position position, Gate gate){
        this.cells[position.getX()][position.getY()].setGate(gate);
        this.cells[position.getX()][position.getY()].setType(gate.getType());
    }

    private ArrayList<Actor> enemies;
    private ArrayList<Gate> gates;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.gates = new ArrayList<>();
        cells = generateCells(width, height, defaultCellType);
    }

    public GameMap(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Cell[][] generateCells(int width, int height, CellType defaultCellType) {
        Cell[][] cells = new Cell[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y, defaultCellType);
            }
        }
        return cells;
    }

    public void setEnemies(ArrayList<Actor> enemies) {
        this.enemies = enemies;
    }

    public void setPlayerStats(Player originalPlayer) {
        player.setPlayerMapLevel(originalPlayer.getPlayerMapLevel());
        player.setInventory(originalPlayer.getInventory());
        player.setWaterLevel(originalPlayer.getWaterLevel());
        player.setHealth(originalPlayer.getHealth());
        player.setDamage(originalPlayer.getDamage());
        player.setMoney(originalPlayer.getMoney());
    }

    public void setCells(Cell[][] cells){
        this.cells = cells;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void setCellActor(int x, int y, Actor actor) {
        this.cells[x][y].setActor(actor);
    }
    public void setCellActorbyPosition(Position position, Actor actor) {
        this.cells[position.getX()][position.getY()].setActor(actor);
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayerPosition(Position position){ //TODO DELELTE???
        this.player.setPosition(position);
    }

    public void addEnemy(Actor enemy) {
        this.enemies.add(enemy);
        cells[enemy.getX()][enemy.getY()].setActor(enemy);
    }

    public void addDoor(Gate gate) {
        this.gates.add(gate);
    }


    public void setGates(ArrayList<Gate> gates){
        this.gates = gates;
    }

    public void removeDeadEnemies() {
        for (Actor enemy : enemies) {
            if (enemy.getHealth() < 0) {

                if (cells[enemy.getX()][enemy.getY()].getItem() == null) {
                    Coin money = new Coin(enemy.getPosition(), enemy.getCoinValue());
                    cells[enemy.getX()][enemy.getY()].setItem(money);
                    this.items.add(money);
                }
                cells[enemy.getX()][enemy.getY()].setActor(null);

                enemies.remove(enemy);
                break;
            }
        }
    }
    public void removeItem(Item item){
        this.items.remove(item);
    }
    public void addItem(Item item, Position position) {
        this.items.add(item);
        setCellItem(item, position);
    }

    public void setCellItem (Item item, Position position){
        cells[position.getX()][position.getY()].setItem(item);
    }

    public void removeItem(Position position) {  //TODO DELELETE??????
        cells[position.getX()][position.getY()].setItem(null);

    }

    public void moveEnemies() {
        int i = 0;
        for (Actor enemy : enemies) {
            enemy.move(this);
            i++;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getRefreshStartCoordinates(int visibleSize) {
        int playerX = player.getX();
        int playerY = player.getY();

        int side = (visibleSize - 1) / 2;

        int startX = playerX - side;
        int startY = playerY - side;


        if (playerX - side < 0) {
            startX = 0;
        } else if (playerX + side >= width - 1) {
            startX = width - visibleSize;
        }

        if (playerY - side < 0) {
            startY = 0;
        } else if (playerY + side >= height - 1) {
            startY = height - visibleSize;
        }

        return new int[]{startX, startY};
    }

    public ArrayList<Position> getPossibleBotMoves(Position currentPosition) {
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        int[][] coordinateDifferences = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
        };
        Cell neighbor;
        ArrayList<Position> stepablePositions = new ArrayList<>();
        for (int[] difference : coordinateDifferences) {
            neighbor = this.getCell(x +difference[0], y + difference[1]);
            if (neighbor.getActor() != null && (neighbor.getActor().getTileName().equals("player")
                    || neighbor.getActor().getTileName().equals("player2"))) {

                neighbor.getActor().setHealth(
                        neighbor.getActor().getHealth() - this.getCell(x, y).getActor().getDamage()
                );

                ArrayList<Position> playerPosition = new ArrayList<>();
                playerPosition.add(neighbor.getPosition());
                return playerPosition;

            } else if (neighbor.getType().getCanStepOn() && neighbor.getActor() == null) {
                stepablePositions.add(neighbor.getPosition());
            }
        }
        return stepablePositions;
    }

    public ArrayList<Position> getPossibleNPCMoves(Position position) {
        int x = position.getX();
        int y = position.getY();
        int[][] coordinateDifferences = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
        };
        Cell neighbor;
        ArrayList<Position> stepAbleCells = new ArrayList<>();
        for (int[] difference : coordinateDifferences) {
            neighbor = this.getCell(x + difference[0], y + difference[1]);
            if (neighbor.getType().getCanStepOn() && neighbor.getActor() == null) {
                stepAbleCells.add(neighbor.getPosition());
            }
        }
        return stepAbleCells;
    }

    public double getDistanceOfCells(Position basePosition, Position targetPosition) { //TODO DELETE?????????
        double dX = Math.abs(basePosition.getX() - targetPosition.getX());
        double dY = Math.abs(basePosition.getY() - targetPosition.getY());//base pos . get x ..........................
        return (dX + dY) / 2;
    }

}
