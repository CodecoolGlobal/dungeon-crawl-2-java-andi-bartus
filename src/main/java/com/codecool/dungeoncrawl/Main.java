package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
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
        ui.add(new Label("Inventory: "), 0, 1);
        ui.add(inventoryLabel, 0, 2);

        ui.add(inventorycanvas,0,10);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

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
        }
    }

    private void movement(int dx, int dy){
        map.getPlayer().move(dx, dy);
        map.removeDeadSkeletons();
        map.moveSkeletons();
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

        for (int i = 0; i < map.getPlayer().getInventory().size(); i++) {
            Tiles.drawTile(inventoryContext,map.getPlayer().getInventory().get(i), 2, i);

        }


    }
}
