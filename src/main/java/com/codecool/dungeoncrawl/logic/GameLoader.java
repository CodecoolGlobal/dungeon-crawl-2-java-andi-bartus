package com.codecool.dungeoncrawl.logic;

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
            ArrayList<JsonObject> maps = new ArrayList<>();

            for (Object object : jsonArray) {
                maps.add((JsonObject) object);
            }

            for(JsonObject map : maps){
                String width = map.get("width").toString();
                String height = map.get("height").toString();
                JsonArray rows = map.get("cells").getAsJsonArray();
                for (JsonElement row : rows){
                    JsonArray rowAsArray = row.getAsJsonArray();
                    for (JsonElement cell : rowAsArray){
                        JsonObject cellObject = cell.getAsJsonObject();

                        System.out.println(cellObject);
                    }
                }
            }
            System.out.println(maps.get(0));



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
}
