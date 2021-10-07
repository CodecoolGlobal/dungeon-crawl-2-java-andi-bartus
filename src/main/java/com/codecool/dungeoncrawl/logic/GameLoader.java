package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Position;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLoader {
    private static final Type TYPE = new TypeToken<List<GameMap>>(){}.getType();

    public static void loadGame() {
        try{
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader("src/main/resources/saves/asd.json"));
            ArrayList<JsonObject> jsonMaps = new ArrayList<>();
            ArrayList<GameMap> maps = new ArrayList<GameMap>();

            for (Object object : jsonArray) {
                jsonMaps.add((JsonObject) object);
            }

            for(JsonObject map : jsonMaps){

            }
            System.out.println(jsonMaps.get(0));



            /*Map<?, ?> map = gson.fromJson(reader, Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
            reader.close();*/
            //JsonReader jsonReader = new JsonReader(new FileReader(filename));
            //List<GameMap> maps = gson.fromJson(jsonReader, TYPE);
            //System.out.println(maps.toString());
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
}
