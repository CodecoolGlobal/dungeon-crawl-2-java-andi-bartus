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
        tileMap.put("barMan", new Tile(28, 4));
        tileMap.put("player2", new Tile(27, 4));
        tileMap.put("player3", new Tile(26, 4));
        tileMap.put("player", new Tile(30, 4));
        tileMap.put("boots", new Tile(7, 23));
        tileMap.put("hat", new Tile(14, 24));
        tileMap.put("horse", new Tile(28, 7));
        tileMap.put("skeleton", new Tile(29, 6));

        tileMap.put("gun", new Tile(7, 31));
        tileMap.put("skull", new Tile(0, 15));
        tileMap.put("tombStone", new Tile(1, 14));//0:14 or 1:14 ?
        tileMap.put("skull1", new Tile(1, 15));
        tileMap.put("skull2", new Tile(0, 15));
        tileMap.put("scorpio", new Tile(25, 5));
        tileMap.put("tequila", new Tile(16, 25));
        tileMap.put("tequila2", new Tile(25, 23));
        tileMap.put("tequila3", new Tile(24, 23));
        tileMap.put("tequila2", new Tile(23, 24));
        tileMap.put("tequila3", new Tile(23, 25));

        tileMap.put("drink1", new Tile(16, 26));
        tileMap.put("drink2", new Tile(17, 25));
        tileMap.put("drink3", new Tile(18, 26));
        tileMap.put("saloonWall", new Tile(1, 1));
        tileMap.put("redHouse1", new Tile(0, 20));
        tileMap.put("redHouse2", new Tile(1, 20));
        tileMap.put("redHouse3", new Tile(0, 21));
        tileMap.put("redHouse4", new Tile (1, 21));
        tileMap.put("churchTop1", new Tile(25, 12));
        tileMap.put("churchTop2", new Tile(2, 12));
        tileMap.put("churchTopMap2", new Tile(26, 12));
        tileMap.put("barLeg", new Tile(25, 13));
        tileMap.put("churchHouse", new Tile(0, 13));
        tileMap.put("door", new Tile(8, 10));
        tileMap.put("closedDoor", new Tile(7, 10));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("town_road", new Tile(1,0));
        tileMap.put("house_base_left", new Tile(10, 16));
        tileMap.put("house_base_center", new Tile(11, 16));
        tileMap.put("house_base_right", new Tile(12, 16));
        tileMap.put("house_wall", new Tile(13, 16));
        tileMap.put("house_window_1", new Tile(13, 15));
        tileMap.put("house_window_2", new Tile(14, 16));
        tileMap.put("house_roof_left", new Tile(10, 15));
        tileMap.put("house_roof_straight", new Tile(11, 15));
        tileMap.put("house_roof_right", new Tile(12, 15));
        tileMap.put("house_door", new Tile(15, 16));
        tileMap.put("star", new Tile(30, 29));
        tileMap.put("S", new Tile(24, 31));
        tileMap.put("A", new Tile(19, 30));
        tileMap.put("L", new Tile(30, 30));
        tileMap.put("O", new Tile(20, 31));
        tileMap.put("N", new Tile(19, 31));
        tileMap.put("G", new Tile(25, 30));
        tileMap.put("U", new Tile(26, 31));
        tileMap.put("saloon_door", new Tile(5, 7));
        tileMap.put("gun_store_door", new Tile(8, 9));
        tileMap.put("bigBoy2", new Tile(30, 6));
        tileMap.put("bigBoy", new Tile(31, 6));
        tileMap.put("sky", new Tile(8, 5));
        tileMap.put("gate" , new Tile(0,7));
        tileMap.put("NPC1", new Tile(25, 7));
        tileMap.put("NPC2", new Tile(31, 7));
        tileMap.put("NPC3", new Tile(25, 4));
        tileMap.put("NPC4", new Tile(31, 4));
        tileMap.put("NPC5", new Tile(27, 1));
        tileMap.put("NPC6", new Tile(26, 1));
        tileMap.put("saloonFloor", new Tile(0, 1));
        tileMap.put("barLeftTop", new Tile(16, 16));
        tileMap.put("barLeftDown", new Tile(16, 18));
        tileMap.put("barCenterTop", new Tile(17, 16));
        tileMap.put("barCenterDown", new Tile(17, 18));
        tileMap.put("barRightTop", new Tile(18, 16));
        tileMap.put("barRightDown", new Tile(18, 18));

        tileMap.put("poker1", new Tile(16, 19));
        tileMap.put("poker2", new Tile(17, 19));
        tileMap.put("poker3", new Tile(18, 19));
        tileMap.put("poker4", new Tile(16, 20));
        tileMap.put("poker5", new Tile(18, 20));
        tileMap.put("poker6", new Tile(16, 21));
        tileMap.put("poker7", new Tile(17, 21));
        tileMap.put("poker8", new Tile(18, 21));
        tileMap.put("ace", new Tile(20, 16));


        tileMap.put("table1", new Tile(0, 16));
        tileMap.put("table2", new Tile(1, 16));
        tileMap.put("table3", new Tile(2, 16));
        tileMap.put("table4", new Tile(0, 17));
        tileMap.put("table5", new Tile(1, 17));
        tileMap.put("table6", new Tile(2, 17));
        tileMap.put("table7", new Tile(0, 18));
        tileMap.put("table8", new Tile(1, 18));
        tileMap.put("table9", new Tile(2, 18));
        tileMap.put("samli", new Tile(2, 8));



        tileMap.put("revolver", new Tile(5, 31));
        tileMap.put("uzi", new Tile(9, 31));
        tileMap.put("sniper", new Tile(10, 31));
        tileMap.put("coin", new Tile(19, 28));

    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
