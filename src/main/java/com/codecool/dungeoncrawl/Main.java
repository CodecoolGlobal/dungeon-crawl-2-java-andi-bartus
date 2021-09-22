package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Star;
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

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);

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
                int x = map.getPlayer().getX();
                int y = map.getPlayer().getY();
                map.getPlayer().addToInventory(map.getCell(x, y).getItem());
                map.removeItem(map.getCell(x, y));
                refresh();
            case A:
                map.getCell(40, 2).setType(CellType.FLOOR);
                refresh();
                break;
            case N:
                map.getCell(41, 2).setType(CellType.FLOOR);
                refresh();
                break;
            case D:
                map.getCell(42, 2).setType(CellType.FLOOR);
                refresh();
                break;
            case I:
                map.getCell(43, 2).setType(CellType.FLOOR);
                refresh();
                break;



        }//restart?
    }

    private void movement(int dx, int dy){
        if (map.getPlayer().getWaterLevel() > 0){
            map.getPlayer().setWaterLevel(map.getPlayer().getWaterLevel()-1);
        }
        else {
            map.getPlayer().setHealth(map.getPlayer().getHealth()-1);
        }

        if(map.getPlayer().getHealth() > 0){
            map.getPlayer().movePlayer(dx, dy);
            map.removeDeadEnemies();
            map.moveEnemies();
        }
        else if(map.getPlayer().getHealth() < 1){
            map.getPlayer().setHealth(0);
            map.getPlayer().setTileNameToTombStone();
        }
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
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
        healthLabel.setText("" + map.getPlayer().getHealth());
        waterLevelLabel.setText("" + map.getPlayer().getWaterLevel());
        for (int i = 0; i < map.getPlayer().getInventory().size(); i++) {
            Tiles.drawTile(inventoryContext,map.getPlayer().getInventory().get(i), 2, i);

        }


    }
}
