package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Position;
import com.codecool.dungeoncrawl.logic.items.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameLoader {

    public static void loadGame() {
        try{
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("src/main/resources/saves/asd.json"));
            ArrayList<JsonObject> jsonMaps = new ArrayList<>();
            ArrayList<GameMap> maps = new ArrayList<GameMap>();
            ArrayList<Item> items = new ArrayList<>();

            for (Object object : jsonArray) {
                jsonMaps.add((JsonObject) object);
            }

            for(JsonObject map : jsonMaps){

            }
            System.out.println(jsonMaps.get(0));


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public GameMap loadMap(JsonObject jsonMap, GameMap map){
        int[] mapSizes = getMapSizes(jsonMap);

        return map;
    }

    public int[] getMapSizes(JsonObject jsonMap){
        int width = Integer.parseInt(jsonMap.get("width").toString());
        int height = Integer.parseInt(jsonMap.get("height").toString());

        return new int[]{width, height};
    }

    public void getCellsOfMap(JsonObject jsonMap, GameMap map){
        Cell[][] cells = new Cell[map.getWidth()][map.getHeight()];
        int x = 0;
        int y = 0;
        JsonArray rows = jsonMap.get("cells").getAsJsonArray();
        for (JsonElement row : rows){
            JsonArray rowAsArray = row.getAsJsonArray();
            for (JsonElement jsonCell : rowAsArray){
                Cell cell = getCell(jsonCell.getAsJsonObject());
                x++;
                cells[x][y] = cell;
            }
            x = 0;
            y++;
        }
        map.setCells(cells);
    }

    public Cell getCell(JsonObject cellObject){
        Position cellPosition = getObjectPosition(cellObject);
        CellType type = getCellType(cellObject);
        return new Cell(cellPosition, type);
    }

    public CellType getCellType(JsonObject jsonCell){
        String jsonCellType = jsonCell.get("type").getAsJsonObject().toString();
        return CellType.valueOf(jsonCellType);
    }

    public Position getObjectPosition(JsonObject jsonObject){
        int x = Integer.parseInt(jsonObject.get("position").getAsJsonObject().get("x").toString());
        int y = Integer.parseInt(jsonObject.get("position").getAsJsonObject().get("y").toString());
        return new Position(x,y);
    }

    public String getName(JsonObject jsonObject){
        return jsonObject.get("name").toString();
    }

    public int getCoinValue(JsonObject jsonObject){
        return jsonObject.get("value").getAsInt();
    }

    public Item getItem(JsonObject jsonObject){
        Position position = getObjectPosition(jsonObject);
        String name = getName(jsonObject);
        switch (name){
            case "star":
                return new Star(position, name);
            case "chick":
                return new Chick(position, name);
            case "coin":
                int value = getCoinValue(jsonObject);
                return new Coin(position, value);
            case "gun":
                return new Gun(position, name);
            case "hat":
                return new Hat(position, name);
            case "rose":
                return new Rose(position, name);
            case "tequila":
                return new Tequila(position, name);
        }
        return null;
    }

    public void getItems(JsonObject jsonMap, GameMap map){
        ArrayList<Item> items = new ArrayList<>();

        JsonArray jsonItems = jsonMap.get("items").getAsJsonArray();
        for(JsonElement jsonItem : jsonItems){
            Item item = getItem(jsonItem.getAsJsonObject());
            items.add(item);
        }

        map.setItems(items);
    }

}
