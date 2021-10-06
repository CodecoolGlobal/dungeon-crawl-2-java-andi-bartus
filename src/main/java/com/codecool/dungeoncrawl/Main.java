package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.queries.Queries;
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
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        popupWindow.setTitle("Save the game");

        Label fileNameLabel = new Label("Filename:");
        TextField fileName = new TextField();
        fileName.setMaxWidth(300);
        Button cancelButton = new Button("Cancel");
//        cancelButton.setTranslateX(0);
//        cancelButton.setTranslateY(0);
        Button saveButton = new Button("Save");
//        saveButton.setTranslateX(0);
//        saveButton.setTranslateY(0);

        cancelButton.setOnAction(e -> popupWindow.close());
        saveButton.setOnAction(e -> { // todo outsource it to a method
            fileNameToSave = fileName.getText();
            if (names.contains(fileNameToSave)){
                System.out.println("nonononoNOOOno");
                //TODO make it a class and make a closePopup method.
                areYouSurePopup();
                popupWindow.close(); // todo delete it
            } else {
                popupWindow.close();
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

    public void areYouSurePopup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("confirmation");
        alert.setContentText("Would you like to overwrite the already existing state?");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<ButtonType> clickedButton = alert.showAndWait();
        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.OK ) {
            try {
                    save(fileNameToSave);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
        } else {
            alert.close();
        }


//        Dialog<String> dialog = new TextInputDialog("");
//        dialog.setTitle("ARE YOU SURE ???");
//        dialog.setHeaderText("Would you like to overwrite the already existing state?");


//        Stage areYouSurePopup = new Stage();
//        areYouSurePopup.initModality(Modality.APPLICATION_MODAL);
//        areYouSurePopup.setTitle("ARE YOU SURE???");
//
//        Label message = new Label("Would you like to overwrite the already existing state?");
    }

    private void popupForLoad() {
        List<String> dialogData = dbManager.getAllNames();
        ChoiceDialog<String> dialog = new ChoiceDialog(dialogData.get(0), dialogData);
        dialog.setHeaderText("Choose where to load save from");
        Optional<String> result = dialog.showAndWait();
        String selected = "";
        if (result.isPresent()) {
            selected = result.get();
            System.out.println(selected);
            //todo call load function;
        }
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCombination loadCombination = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        }
        if (saveCombination.match(keyEvent))
            popup();
        if (loadCombination.match(keyEvent))
            popupForLoad();
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
//            case S:
//                Player player = maps.get(currentMap).getPlayer();
//                dbManager.savePlayer(player);
//                break;
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
        List<String> names = dbManager.getAllNames();
        if (names.contains(saveName)) {
            //ToDo update DB with new save
        } else {
            dbManager.saveJSON(saveName, new_save.toString());
        }

    }

}
