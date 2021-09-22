package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Scorpion;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.actors.BigBoy;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/map2.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Skeleton(cell));//make generic ? (<enemy> ? for multiple enemy types)
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Star(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(map.getPlayer());
                            map.getPlayer().setTileNameToPlayer2();
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new Scorpion(cell));
                            break;
                        case 'W':
                            cell.setType(CellType.FLOOR);
                            map.addEnemy(new BigBoy(cell));
                            break;
                        case 'b':
                            cell.setType(CellType.FLOOR);
                            BigBoy bigBoy2 = new BigBoy(cell);
                            bigBoy2.setTileNameToBigBoy2();
                            map.addEnemy(bigBoy2);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Gun(cell));
                            break;

                        case 't':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Tequila(cell));
                            break;
                        case 'a':
                            cell.setType(CellType.RED_HOUSE1);
                            break;
                        case 'B':
                            cell.setType(CellType.RED_HOUSE2);
                            break;
                        case 'd':
                            cell.setType(CellType.RED_HOUSE3);
                            break;
                        case 'e':
                            cell.setType(CellType.RED_HOUSE4);
                            break;
                        case 'x':
                            cell.setType(CellType.CHURCH_TOP);
                            break;
                        case 'w':
                            cell.setType(CellType.CHURCH_TOP2);
                            break;
                        case 'y':
                            cell.setType(CellType.CHURH_HOUSE);
                            break;
                        case '0':
                            cell.setType(CellType.TOMB_STONE);
                            break;
                        case '1':
                            cell.setType(CellType.SKULL1);
                            break;
                        case '2':
                            cell.setType(CellType.SKULL2);
                            break;
                        case '-':
                            cell.setType(CellType.TOWN_ROAD);
                            break;
                        case 'Q':
                            cell.setType(CellType.HOUSE_BASE_LEFT);
                            break;
                        case 'W':
                            cell.setType(CellType.HOUSE_BASE_CENTER);
                            break;
                        case 'E':
                            cell.setType(CellType.HOUSE_BASE_RIGHT);
                            break;
                        case 'R':
                            cell.setType(CellType.HOUSE_WALL);
                            break;
                        case 'T':
                            cell.setType(CellType.HOUSE_WINDOW_1);
                            break;
                        case 'Z':
                            cell.setType(CellType.HOUSE_WINDOW_2);
                            break;
                        case 'U':
                            cell.setType(CellType.HOUSE_ROOF_LEFT);
                            break;
                        case 'I':
                            cell.setType(CellType.HOUSE_ROOF_STRAIGHT);
                            break;
                        case 'O':
                            cell.setType(CellType.HOUSE_ROOF_RIGHT);
                            break;
                        case 'P':
                            cell.setType(CellType.HOUSE_DOOR);
                            break;
                        case 'A':
                            cell.setType(CellType.S);
                            break;
                        case 'S':
                            cell.setType(CellType.A);
                            break;
                        case 'D':
                            cell.setType(CellType.L);
                            break;
                        case 'F':
                            cell.setType(CellType.O);
                            break;
                        case 'G':
                            cell.setType(CellType.N);
                            break;
                        case 'H':
                            cell.setType(CellType.G);
                            break;
                        case 'J':
                            cell.setType(CellType.U);
                            break;
                        case 'K':
                            cell.setType(CellType.SALOON_DOOR);
                            break;
                        case '&':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'q':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Boots(cell));
                            break;
                        case 'Ü':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Hat(cell));
                            break;
                        case 'ó':
                            cell.setType(CellType.HORSE);
                            break;


                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
