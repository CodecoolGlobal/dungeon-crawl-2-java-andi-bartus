package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles2.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    public static Map<String, Tile> getTileMap() {
        return tileMap;
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(6, 1));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player2", new Tile(27, 4));
        tileMap.put("player", new Tile(30, 4));
        tileMap.put("boots", new Tile(7, 23));
        tileMap.put("hat", new Tile(14, 24));
        tileMap.put("horse", new Tile(28, 7));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("star", new Tile(28, 25));
        tileMap.put("gun", new Tile(7, 31));
        tileMap.put("skull", new Tile(0, 15));
        tileMap.put("tombStone", new Tile(1, 14));//0:14 or 1:14 ?
        tileMap.put("skull1", new Tile(1, 15));
        tileMap.put("skull2", new Tile(0, 15));
        tileMap.put("scorpio", new Tile(25, 5));
        tileMap.put("tequila", new Tile(16, 25));
        tileMap.put("redHouse1", new Tile(0, 20));
        tileMap.put("redHouse2", new Tile(1, 20));
        tileMap.put("redHouse3", new Tile(0, 21));
        tileMap.put("redHouse4", new Tile (1, 21));
        tileMap.put("churchTop1", new Tile(25, 12));
        tileMap.put("churchTop2", new Tile(2, 12));
        tileMap.put("churchHouse", new Tile(0, 13));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("bigBoy2", new Tile(30, 6));
        tileMap.put("bigBoy", new Tile(31, 6));


    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
