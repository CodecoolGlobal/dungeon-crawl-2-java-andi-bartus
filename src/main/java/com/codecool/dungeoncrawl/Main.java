package com.codecool.dungeoncrawl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Chick;
import com.codecool.dungeoncrawl.logic.items.Gun;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {
    List<GameMap> maps = MapLoader.loadAllMaps();
    int currentMap = 0;
    int lastMap;
    int visibleSize = 25;
    String fileNameToSave;
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
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
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
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public void popup() {
        List<String> names = dbManager.getAllNames();

        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("This is a popup");

        Label fileNameLabel = new Label("Filename:");
        TextField fileName = new TextField();
        fileName.setMaxWidth(300);
        Button cancelButton = new Button("Cancel");
        cancelButton.setTranslateX(0);
        cancelButton.setTranslateY(0);
        Button saveButton = new Button("Save");
        saveButton.setTranslateX(0);
        saveButton.setTranslateY(0);

        cancelButton.setOnAction(e -> popupWindow.close());
        saveButton.setOnAction(e -> {
            fileNameToSave = fileName.getText();
            popupWindow.close();

            if (names.contains(fileNameToSave)){
                System.out.println("nonononoNOOOno");
                //TODO pop-up window --> theres a name like this...
            } else {
                try {
                    save(fileNameToSave);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(fileNameLabel, fileName, saveButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 500, 500);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
        if (saveCombination.match(keyEvent))
            popup();
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
                GameMap actualMap = maps.get(currentMap);
                int x = actualMap.getPlayer().getX();
                int y = actualMap.getPlayer().getY();
                if (actualMap.getPlayer().getHealth()>0) {
                    if (actualMap.getCell(x, y).getItem() instanceof Chick && actualMap.getPlayer().canPickUpChick()) {
                        end();
                    } else {
                        actualMap.getCell(x, y).getItem().useItem(actualMap.getPlayer());                                            }
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
//            case S:
//                Player player = maps.get(currentMap).getPlayer();
//                dbManager.savePlayer(player);
//                break;
        }
    }

    private void movement(int dx, int dy){
        GameMap actaulMap = maps.get(currentMap);
        if (actaulMap.getPlayer().getWaterLevel() > 0 && actaulMap.getPlayer().getHealth() > 0){
            actaulMap.getPlayer().setWaterLevel(actaulMap.getPlayer().getWaterLevel()-1);
        } else if (actaulMap.getPlayer().getHealth() > 0){
            actaulMap.getPlayer().setHealth(actaulMap.getPlayer().getHealth()-1);
        }
        if(actaulMap.getPlayer().getHealth() > 0){
            actaulMap.getPlayer().movePlayer(dx, dy, actaulMap);
            actaulMap.removeDeadEnemies();
            actaulMap.moveEnemies();
            if (currentMap != actaulMap.getPlayer().getPlayerMapLevel()) {
                this.lastMap = currentMap;
                this.currentMap = actaulMap.getPlayer().getPlayerMapLevel();
                actaulMap.setPlayerStats(maps.get(lastMap).getPlayer());
            }
        }
        if(actaulMap.getPlayer().getHealth() < 1){
            actaulMap.getPlayer().setHealth(0);
            actaulMap.getPlayer().setTileNameToTombStone();
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

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
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

    public void save(String saveName) throws SQLException {
        JsonObject new_save = new JsonObject(); // TODO bens

        dbManager.saveJSON(saveName, new_save.toString());

    }

}
