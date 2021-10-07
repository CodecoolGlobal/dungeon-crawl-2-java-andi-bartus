package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {

    private static final String[] mapFileNames = {"/map.txt", "/map2.txt","/gunStore.txt", "/map3.txt"};

    public static ArrayList<GameMap> loadAllMaps() {
        ArrayList<GameMap> allMaps = new ArrayList<>();
        for(String mapName : mapFileNames) {
            allMaps.add(loadMap(mapName));
        }
        return allMaps;
    }

    public static GameMap loadMap(String mapFileName) {
        InputStream is = MapLoader.class.getResourceAsStream(mapFileName);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();
        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    Position position = cell.getPosition();
                    Position entityPosition = new Position(position.getX(), position.getY());
                    Actor actor;
                    Item item;
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
                       case 'k':
                            cell.setType(CellType.TOWN_ROAD);
                            item = new Star(position, "star");
                            map.addItem(item, position);
                            break;
                        case '@':
                            cell.setType(CellType.TOWN_ROAD);
                            Player player2 = new Player(entityPosition, "player2");
                            map.setPlayer(player2);
                            cell.setActor(player2);
                            break;
                        case 'ß':
                            cell.setType(CellType.SALOONFLOOR);
                            Player player3 = new Player(entityPosition, "player3");
                            map.setPlayer(player3);
                            cell.setActor(player3);
                            break;
                        case 'c':
                            cell.setType(CellType.FLOOR);
                            actor = new Scorpion(entityPosition, "scorpion");
                            map.addEnemy(actor);
                            cell.setActor(actor);
                            break;
                        case 'W':
                            cell.setType(CellType.FLOOR);
                            actor = new BigBoy(entityPosition, "bigBoy");
                            map.addEnemy(actor);
                            cell.setActor(actor);
                            break;
                        case 'b':
                            cell.setType(CellType.TOWN_ROAD);
                            actor = new BigBoy(entityPosition, "bigBoy2");
                            map.addEnemy(actor);
                            cell.setActor(actor);
                            break;
                        case 'g':
                            cell.setType(CellType.SALOONFLOOR);
                            map.addItem(new Gun(position, "gun"), position);
                            break;
                        case 't':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Tequila(position, "tequila"), position);
                            break;
                        case 'Y':
                            cell.setType(CellType.TOWN_ROAD);
                            map.addItem(new Tequila(position, "tequila2"), position);
                            break;
                        case 'X':
                            cell.setType(CellType.SALOONFLOOR);
                            map.addItem(new Tequila(position, "tequila3"), position);
                            break;
                        case 'a':
                            cell.setType(CellType.RED_HOUSE1);
                            break;
                        case 'B':
                            cell.setType(CellType.RED_HOUSE2);
                            break;
                        case 'C':
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
                            cell.setType(CellType.CHURCH_HOUSE);
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
                        case 'D':
                            cell.setType(CellType.GATE);
                            cell.addDoor(new Gate(position, 1, CellType.GATE));
                            map.addDoor(cell.getGate());
                            break;
                        case 'd':
                            cell.setType(CellType.GATE);
                            cell.addDoor(new Gate(position, 0, CellType.GATE));
                            map.addDoor(cell.getGate());
                            break;
                        case 'É':
                            cell.setType(CellType.SALOON_DOOR);
                            cell.addDoor(new Gate(position, 3, CellType.SALOON_DOOR));
                            map.addDoor(cell.getGate());
                            break;
                        case 'é':
                            cell.setType(CellType.SALOON_DOOR);
                            cell.addDoor(new Gate(position, 1, CellType.SALOON_DOOR));
                            map.addDoor(cell.getGate());
                            break;
                        case 'Í':
                            cell.setType(CellType.GUN_STORE_DOOR);
                            cell.addDoor(new Gate(position, 2, CellType.GUN_STORE_DOOR));
                            map.addDoor(cell.getGate());
                            break;
                        case 'í':
                            cell.setType(CellType.GUN_STORE_DOOR);
                            cell.addDoor(new Gate(position, 1, CellType.GUN_STORE_DOOR));
                            map.addDoor(cell.getGate());
                            break;
                        case '-':
                            cell.setType(CellType.TOWN_ROAD);
                            break;
                        case 'Q':
                            cell.setType(CellType.HOUSE_BASE_LEFT);
                            break;
                        case 'l':
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
                        case 'L':
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
                        case '&':
                            cell.setType(CellType.FLOOR);
                            Player player = new Player(entityPosition, "player");
                            map.setPlayer(player);
                            cell.setActor(player);
                            break;
                        case 'q':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Boots(position, "boots"), position);
                            break;
                        case 'Ü':
                            cell.setType(CellType.FLOOR);
                            map.addItem(new Hat(position, "hat"), position);
                            break;
                        case 'ó':
                            cell.setType(CellType.HORSE);
                            break;
                        case ':':
                            cell.setType(CellType.SKY);
                            break;
                        case '?':
                            cell.setType(CellType.CHURCHTOP2);
                            break;
                        case 'N':
                            cell.setType(CellType.TOWN_ROAD);
                            actor = new FriendlyNPC(entityPosition, FriendlyNPC.getRandomNPCName());
                            map.addEnemy(actor);
                            cell.setActor(actor);
                            break;
                        case '_':
                            cell.setType(CellType.SALOONFLOOR);
                            break;
                        case '(':
                            cell.setType(CellType.BARLEFTTOP);
                            break;
                        case '[':
                            cell.setType(CellType.BARLEFTDOWN);
                            break;
                        case '=':
                            cell.setType(CellType.BARCENTERTOP);
                            break;
                        case '`':
                            cell.setType(CellType.BARCENTERDOWN);
                            break;
                        case ')':
                            cell.setType(CellType.BARRIGHTTOP);
                            break;
                        case ']':
                            cell.setType(CellType.BARRIGHTDOWN);
                            break;
                        case '|':
                            cell.setType(CellType.BARLEG);
                            break;
                        case ';':
                            cell.setType(CellType.DRINK1);
                            break;
                        case '%':
                            cell.setType(CellType.DRINK2);
                            break;
                        case '!':
                            cell.setType(CellType.DRINK3);
                            break;
                        case '+':
                            cell.setType(CellType.BARMAN);
                            break;
                        case 'o':
                            cell.setType(CellType.SALOONWALL);
                            break;
                        case 'm':
                            cell.setType(CellType.SALOONFLOOR);
                            actor = new Gangsta(entityPosition, "gangsta");
                            map.addEnemy(actor);
                            cell.setActor(actor);
                            break;
                        case 'V':
                            cell.setType(CellType.TABLE1);
                            break;
                        case 'z':
                            cell.setType(CellType.TABLE2);
                            break;
                        case 'Á':
                            cell.setType(CellType.TABLE3);
                            break;
                        case 'i':
                            cell.setType(CellType.TABLE4);
                            break;
                        case 'p':
                            cell.setType(CellType.TABLE5);
                            break;
                        case 'ő':
                            cell.setType(CellType.TABLE6);
                            break;
                        case 'ú':
                            cell.setType(CellType.TABLE7);
                            break;
                        case 'ü':
                            cell.setType(CellType.TABLE8);
                            break;
                        case 'ö':
                            cell.setType(CellType.TABLE9);
                            break;
                        case 'M':
                            cell.setType(CellType.SAMLI);
                            break;
                        case 'n':
                            cell.setType(CellType.POKER1);
                            break;
                        case 'f':
                            cell.setType(CellType.POKER2);
                            break;
                        case 'h':
                            cell.setType(CellType.POKER3);
                            break;
                        case 'K':
                            cell.setType(CellType.POKER4);
                            break;
                        case 'á':
                            cell.setType(CellType.POKER5);
                            break;
                        case 'Ő':
                            cell.setType(CellType.POKER6);
                            break;
                        case 'Ú':
                            cell.setType(CellType.POKER7);
                            break;
                        case 'Ű':
                            cell.setType(CellType.POKER8);
                            break;
                        case 'Ö':
                            cell.setType(CellType.ACE);
                            break;
                       case 'r':
                            cell.setType(CellType.REVOLVER);
                            break;
                        case 'u':
                            cell.setType(CellType.UZI);
                            break;
                        case 'ű':
                            cell.setType(CellType.SNIPER);
                            break;
                        case '9':
                            cell.setType(CellType.SALOONFLOOR);
                            map.addItem(new Chick(position, "chick"), position);
                            break;
                        case '8':
                            cell.setType(CellType.SALOONFLOOR);
                            map.addItem(new Rose(position, "rose"), position);
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
