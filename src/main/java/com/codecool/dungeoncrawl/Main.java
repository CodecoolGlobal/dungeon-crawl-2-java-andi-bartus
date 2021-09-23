package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.items.Chick;
import com.codecool.dungeoncrawl.logic.items.Gun;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    List<GameMap> maps = MapLoader.loadAllMaps();
    int currentMap = 0;
    int lastMap;
    int visibleSize = 25;
    Canvas canvas = new Canvas(
            visibleSize* Tiles.TILE_WIDTH*1.25,
            visibleSize* Tiles.TILE_WIDTH*1.25);

    Canvas inventorycanvas = new Canvas(
            4 * Tiles.TILE_WIDTH,
            8 * Tiles.TILE_WIDTH);

    GraphicsContext context = canvas.getGraphicsContext2D();
    GraphicsContext inventoryContext = inventorycanvas.getGraphicsContext2D();

    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label waterLevelLabel = new Label();
    Label moneyLabel = new Label();
    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        GridPane ui = new GridPane();
        ui.setStyle("-fx-font-size: 25px");
        ui.setPrefWidth(350);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(new Label("Water level: "), 0, 1);
        ui.add(waterLevelLabel, 1, 1);
        ui.add(new Label("Money: "), 0, 2);
        ui.add(moneyLabel, 1, 2);
        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(inventoryLabel, 0, 4);

        ui.add(inventorycanvas, 0, 10);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setLeft(ui);
        borderPane.setStyle("-fx-background-color:lightgrey;");

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
                if (maps.get(currentMap).getPlayer().getHealth()>0) {
                    if (maps.get(currentMap).getCell(x, y).getItem() instanceof Chick && maps.get(currentMap).getPlayer().canPickUpChick()) {
                        end();
                    } else if (!(maps.get(currentMap).getCell(x, y).getItem() instanceof Gun) && !(maps.get(currentMap).getCell(x, y).getItem() instanceof Chick)) {
                        maps.get(currentMap).getPlayer().addToInventory(maps.get(currentMap).getCell(x, y).getItem());
                        maps.get(currentMap).removeItem(maps.get(currentMap).getCell(x, y));
                    } else if (maps.get(currentMap).getCell(x, y).getItem() instanceof Gun && maps.get(currentMap).getPlayer().checkMoneyForGun()) {
                        maps.get(currentMap).getPlayer().addToInventory(maps.get(currentMap).getCell(x, y).getItem());
                        maps.get(currentMap).removeItem(maps.get(currentMap).getCell(x, y));
                    }
                }
                refresh();
                break;
            case A: //andi
                maps.get(0).getCell(40, 3).setType(CellType.FLOOR);
                refresh();
               break;
            case B: //bence
                maps.get(0).getCell(41, 3).setType(CellType.FLOOR);
               refresh();
                break;
            case P: //peti
                maps.get(0).getCell(42, 3).setType(CellType.FLOOR);
                refresh();
                break;
            case T: //tomi
                maps.get(0).getCell(43, 3).setType(CellType.FLOOR);
                refresh();
                break;
        }
    }

    private void movement(int dx, int dy){
        if (maps.get(currentMap).getPlayer().getWaterLevel() > 0 && maps.get(currentMap).getPlayer().getHealth() > 0){
            maps.get(currentMap).getPlayer().setWaterLevel(maps.get(currentMap).getPlayer().getWaterLevel()-1);
        } else if (maps.get(currentMap).getPlayer().getHealth() > 0){
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
        if(maps.get(currentMap).getPlayer().getHealth() < 1){
            maps.get(currentMap).getPlayer().setHealth(0);
            maps.get(currentMap).getPlayer().setTileNameToTombStone();
        }
        refresh();
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        int[] startCoordinates = maps.get(currentMap).getRefreshStartCoordinates(visibleSize);

        int j = 0;
        int k = 0;

        for (int x = startCoordinates[0]; x < startCoordinates[0] + visibleSize; x++) {
            for (int y = startCoordinates[1]; y < startCoordinates[1] + visibleSize; y++) {
                Cell cell = maps.get(currentMap).getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), k, j);
                } else if(cell.getItem() != null){
                    Tiles.drawTile(context, cell.getItem(), k , j);
                }
                else {
                    Tiles.drawTile(context, cell, k, j);
                }
                j++;
            }
            j=0;
            k++;
        }
        healthLabel.setText("" + maps.get(currentMap).getPlayer().getHealth());
        waterLevelLabel.setText("" + maps.get(currentMap).getPlayer().getWaterLevel());
        moneyLabel.setText("" + maps.get(currentMap).getPlayer().getMoney()+"$");
        for (int i = 0; i < maps.get(currentMap).getPlayer().getInventory().size(); i++) {
            Tiles.drawTile(inventoryContext, maps.get(currentMap).getPlayer().getInventory().get(i), 2, i);

        }
    }


    public void end() {
        Text text = new Text();


        text.setText("You win!");
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 100));
        text.setFill(Color.FUCHSIA);

        text.setX(500);
        text.setY(500);

        Group root = new Group(text);

        GridPane ui = new GridPane();
        ui.setStyle("-fx-font-size: 25px");
        ui.setPrefWidth(350);
        ui.setPadding(new Insets(10));

        Scene sceneOld = primaryStage.getScene();
        double x = sceneOld.getWidth();
        double y = sceneOld.getHeight();

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(canvas, root);
        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(stackPane);
        borderPane.setLeft(ui);
        borderPane.setStyle("-fx-background-color:black;");



        primaryStage.setScene(new Scene(borderPane, x, y));


        primaryStage.setTitle("Sample Application");

        primaryStage.show();
    }

}
