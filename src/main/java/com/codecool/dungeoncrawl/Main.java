package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {
    List<GameMap> maps = MapLoader.loadAllMaps();
    int currentMap = 0;
    int lastMap;
    Canvas canvas = new Canvas(
            maps.get(currentMap).getWidth() * Tiles.TILE_WIDTH,
            maps.get(currentMap).getHeight() * Tiles.TILE_WIDTH);

    Canvas inventorycanvas = new Canvas(
            3 * Tiles.TILE_WIDTH,
            8 * Tiles.TILE_WIDTH);

    GraphicsContext context = canvas.getGraphicsContext2D();
    GraphicsContext inventoryContext = inventorycanvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label waterLevelLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Water level: "), 0, 1);
        ui.add(waterLevelLabel, 1, 1);
        ui.add(new Label("Inventory: "), 0, 2);
        ui.add(inventoryLabel, 0, 3);

        ui.add(inventorycanvas,0,10);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setLeft(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();

        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                movement(0,-1);
                break;
            case DOWN:
                movement(0,1);
                break;
            case LEFT:
                movement(-1,0);
                break;
            case RIGHT:
                movement(1,0);
                break;
            case E:
                int x = maps.get(currentMap).getPlayer().getX();
                int y = maps.get(currentMap).getPlayer().getY();
                maps.get(currentMap).getPlayer().addToInventory(maps.get(currentMap).getCell(x, y).getItem());
                maps.get(currentMap).removeItem(maps.get(currentMap).getCell(x, y));
                refresh();
            case A: //andi
                maps.get(0).getCell(40, 2).setType(CellType.FLOOR);
                refresh();
               break;
            case B: //bence
                maps.get(0).getCell(41, 2).setType(CellType.FLOOR);
               refresh();
                break;
            case P: //peti
                maps.get(0).getCell(42, 2).setType(CellType.FLOOR);
                refresh();
                break;
            case T: //tomi
                maps.get(0).getCell(43, 2).setType(CellType.FLOOR);
                refresh();
                break;





        }//restart?


    }

    private void movement(int dx, int dy){
        if (maps.get(currentMap).getPlayer().getWaterLevel() > 0){
            maps.get(currentMap).getPlayer().setWaterLevel(maps.get(currentMap).getPlayer().getWaterLevel()-1);
        }
        else {
            maps.get(currentMap).getPlayer().setHealth(maps.get(currentMap).getPlayer().getHealth()-1);
        }
        if(maps.get(currentMap).getPlayer().getHealth() > 0){
            maps.get(currentMap).getPlayer().movePlayer(dx, dy);
            maps.get(currentMap).removeDeadEnemies();
            maps.get(currentMap).moveEnemies();
            if (currentMap != maps.get(currentMap).getPlayer().getPlayerMapLevel()) {
                this.lastMap = currentMap;
                this.currentMap = maps.get(currentMap).getPlayer().getPlayerMapLevel();
                maps.get(currentMap).setPlayerStats(maps.get(lastMap).getPlayer());
            }
        }
        else if(maps.get(currentMap).getPlayer().getHealth() < 1){
            maps.get(currentMap).getPlayer().setHealth(0);
            maps.get(currentMap).getPlayer().setTileNameToTombStone();
        }
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < maps.get(currentMap).getWidth(); x++) {
            for (int y = 0; y < maps.get(currentMap).getHeight(); y++) {
                Cell cell = maps.get(currentMap).getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), x , y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + maps.get(currentMap).getPlayer().getHealth());
        waterLevelLabel.setText("" + maps.get(currentMap).getPlayer().getWaterLevel());
        for (int i = 0; i < maps.get(currentMap).getPlayer().getInventory().size(); i++) {
            Tiles.drawTile(inventoryContext, maps.get(currentMap).getPlayer().getInventory().get(i), 2, i);

        }
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap=currentMap;
    }
}
