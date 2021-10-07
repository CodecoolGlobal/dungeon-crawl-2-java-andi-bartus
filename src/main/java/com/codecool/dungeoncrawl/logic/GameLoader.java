package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Position;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameLoader {

    public ArrayList<GameMap> loadGame(ArrayList<GameMap> resultMaps, String filename) {
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(filename));
            ArrayList<JsonObject> jsonMaps = new ArrayList<>();

            for (Object object : jsonArray) {
                jsonMaps.add((JsonObject) object);
            }

            ArrayList<GameMap> maps = new ArrayList<>();
            for (JsonObject jsonMap : jsonMaps) {
                maps.add(loadMap(jsonMap));
            }

            return maps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMaps;
    }

    public GameMap loadMap(JsonObject jsonMap) {
        int[] mapSizes = getMapSizes(jsonMap);
        GameMap map = new GameMap(mapSizes[0], mapSizes[1]);
        getCellsOfMap(jsonMap, map);
        getGates(jsonMap, map);
        getActorsOfMap(jsonMap, map);
        getItems(jsonMap, map);
        getPlayer(jsonMap, map);

        loadObjectsToMap(map);

        return map;
    }

    public void loadObjectsToMap(GameMap map) {
        for (Actor enemy : map.getEnemies()) {
            map.setCellActorbyPosition(enemy.getPosition(), enemy);
        }

        for (Item item : map.getItems()) {
            map.setCellItem(item, item.getPosition());
        }

        for (Gate gate : map.getGates()) {
            map.setCellGateByPosition(gate.getPosition(), gate);
        }
        map.setCellActorbyPosition(map.getPlayer().getPosition(), map.getPlayer());
    }

    public int[] getMapSizes(JsonObject jsonMap) {
        int width = jsonMap.get("width").getAsInt();
        int height = jsonMap.get("height").getAsInt();
        return new int[]{width, height};
    }

    public void getCellsOfMap(JsonObject jsonMap, GameMap map) {
        Cell[][] cells = new Cell[map.getWidth()][map.getHeight()];
        JsonArray rows = jsonMap.get("cells").getAsJsonArray();
        for (int i = 0; i < rows.size(); i++) {
            JsonArray rowAsArray = rows.get(i).getAsJsonArray();
            for (int j = 0; j < rowAsArray.size(); j++) {
                JsonObject jsonObject = rowAsArray.get(j).getAsJsonObject();
                Cell cell = getCell(jsonObject);
                cells[i][j] = cell;
            }
        }
        map.setCells(cells);
    }

    public void getActorsOfMap(JsonObject jsonMap, GameMap map) {
        ArrayList<Actor> enemyList = new ArrayList<>();
        JsonArray enemies = jsonMap.get("enemies").getAsJsonArray();
        for (JsonElement enemy : enemies) {
            enemyList.add(getActor(enemy));
        }
        map.setEnemies(enemyList);
    }

    public Cell getCell(JsonObject cellObject) {
        Position cellPosition = getObjectPosition(cellObject);
        CellType type = getCellType(cellObject);
        return new Cell(cellPosition, type);
    }

    public CellType getCellType(JsonObject jsonCell) {
        String jsonCellType = jsonCell.get("type").toString();
        jsonCellType = jsonCellType.substring(1, jsonCellType.length() - 1);
        return CellType.valueOf(jsonCellType);

    }

    public Position getObjectPosition(JsonObject jsonObject) {
        int x = jsonObject.get("position").getAsJsonObject().get("x").getAsInt();
        int y = jsonObject.get("position").getAsJsonObject().get("y").getAsInt();
        return new Position(x, y);
    }

    public String getName(JsonObject jsonObject) {
        String name = jsonObject.get("name").toString();
        return name.substring(1, name.length() - 1);
    }

    public int getCoinValue(JsonObject jsonObject) {
        if (jsonObject.get("value") == null) {
            return 0;
        } else {
            return jsonObject.get("value").getAsInt();
        }
    }

    public Actor getActor(JsonElement enemy) {
        Position position = getObjectPosition(enemy.getAsJsonObject());
        int health = getHealth(enemy);
        String name = getName(enemy.getAsJsonObject());
        Actor actor;
        if (Objects.equals(name, "scorpion")) {
            actor = new Scorpion(position, name, health);
        } else if (Objects.equals(name, "bigBoy") || Objects.equals(name, "bigBoy2")) {
            actor = new BigBoy(position, name, health);
        } else if (Objects.equals(name, "gangsta")) {
            actor = new Gangsta(position, name, health);
        } else {
            actor = new FriendlyNPC(position, name);
        }
        return actor;
    }

    public int getHealth(JsonElement actor) {
        return actor.getAsJsonObject().get("health").getAsInt();
    }

    public Item getItem(JsonObject jsonObject) {
        Position position = getObjectPosition(jsonObject);
        String name = getName(jsonObject);
        Item item = null;
        switch (name) {
            case "star":
                item = new Star(position, name);
                break;
            case "chick":
                item = new Chick(position, name);
                break;
            case "coin":
                int value = getCoinValue(jsonObject);
                item = new Coin(position, value);
                break;
            case "gun":
                item = new Gun(position, name);
                break;
            case "hat":
                item = new Hat(position, name);
                break;
            case "boots":
                item = new Boots(position, name);
                break;
            case "rose":
                item = new Rose(position, name);
                break;
            case "tequila":
                item =  new Tequila(position, name);
                break;
            case "tequila2":
                item =  new Tequila(position, name);
                break;
            case "tequila3":
                item =  new Tequila(position, name);
                break;
        }
        return item;
    }

    public void getItems(JsonObject jsonMap, GameMap map) {
        ArrayList<Item> items = new ArrayList<>();

        JsonArray jsonItems = jsonMap.get("items").getAsJsonArray();
        for (JsonElement jsonItem : jsonItems) {
            Item item = getItem(jsonItem.getAsJsonObject());
            items.add(item);
        }
        map.setItems(items);
    }


    public void getGates(JsonObject jsonMap, GameMap map) {
        ArrayList<Gate> gates = new ArrayList<>();

        JsonArray jsonGates = jsonMap.get("gates").getAsJsonArray();
        for (JsonElement jsonGate : jsonGates) {
            Gate gate = createGateFromJson(jsonGate.getAsJsonObject());
            gates.add(gate);
        }
        map.setGates(gates);
    }

    public Gate createGateFromJson(JsonObject jsonGate) {
        Position gatePosition = getObjectPosition(jsonGate);
        int newCurrentMap = getNewCurrentMapValueOfGate(jsonGate);
        CellType type = getCellType(jsonGate);
        return new Gate(gatePosition, newCurrentMap, type);
    }

    public int getNewCurrentMapValueOfGate(JsonObject jsonGate) {
        return jsonGate.get("newCurrentMap").getAsInt();
    }

    public void getPlayer(JsonObject jsonMap, GameMap map) {
        JsonObject jsonPlayer = jsonMap.get("player").getAsJsonObject();
        Position position = getObjectPosition(jsonPlayer);
        String name = getName(jsonPlayer);
        int damage = getDamage(jsonPlayer);
        int waterLevel = getWaterLevel(jsonPlayer);
        ArrayList<Item> inventory = getInventory(jsonPlayer);
        int money = getMoney(jsonPlayer);
        int playerMapLevel = getPlayerMapLevel(jsonPlayer);
        int health = getHealth(jsonPlayer);
        int coinValue = getCoinValue(jsonPlayer);

        map.setPlayer(new Player(position, name, damage, waterLevel, inventory, money, playerMapLevel, health, coinValue));
    }

    public int getDamage(JsonObject jsonObject) {
        return jsonObject.get("damage").getAsInt();
    }

    public int getWaterLevel(JsonObject jsonObject) {
        return jsonObject.get("waterLevel").getAsInt();
    }

    public ArrayList<Item> getInventory(JsonObject jsonObject) {
        ArrayList<Item> inventory = new ArrayList<>();
        JsonArray inventoryJSON = jsonObject.get("inventory").getAsJsonArray();

        for (JsonElement itemJSON : inventoryJSON) {
            Item item = getItem(itemJSON.getAsJsonObject());
            inventory.add(item);
        }
        return inventory;
    }

    public int getMoney(JsonObject jsonObject) {
        return jsonObject.get("money").getAsInt();
    }

    public int getPlayerMapLevel(JsonObject jsonObject) {
        return jsonObject.get("playerMapLevel").getAsInt();
    }

    public int getHealth(JsonObject jsonObject) {
        return jsonObject.get("health").getAsInt();
    }

}
