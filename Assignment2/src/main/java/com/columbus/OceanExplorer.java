package com.columbus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class OceanExplorer extends Application {

    private AnchorPane rootPane;
    private OceanMap oceanMap;
    private Ship ship;
    private ImageView shipImageView;
    private PirateShip pirate1, pirate2;
    private final int scale = 50;
    private Label gameOverLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage oceanStage) {
        rootPane = new AnchorPane();
        oceanMap = new OceanMap();
        drawMap();

        ship = new Ship(0, 0, this::onGameOver);
        loadShipImage();

        pirate1 = new PirateShip(5, 5);
        pirate2 = new PirateShip(8, 7);
        rootPane.getChildren().addAll(pirate1.getImageView(), pirate2.getImageView());

        ship.addObserver(pirate1);
        ship.addObserver(pirate2);

        Scene scene = new Scene(rootPane, oceanMap.getDimension() * scale, oceanMap.getDimension() * scale);
        oceanStage.setScene(scene);
        oceanStage.setTitle("Christopher Columbus Sails the Ocean Blue");
        oceanStage.show();

        startSailing(scene);
    }

    private void drawMap() {
        int[][] oceanGrid = oceanMap.getMap();
        int dimension = oceanMap.getDimension();
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (oceanGrid[x][y] == 0) {
                    Rectangle rect = new Rectangle(x * scale, y * scale, scale, scale);
                    rect.setStroke(Color.BLACK);
                    rect.setFill(Color.PALETURQUOISE);
                    rootPane.getChildren().add(rect);
                } else {
                    Image islandImage = new Image(getClass().getResource("/images/island.jpg").toExternalForm(), scale, scale, true, true);
                    ImageView islandView = new ImageView(islandImage);
                    islandView.setX(x * scale);
                    islandView.setY(y * scale);
                    rootPane.getChildren().add(islandView);
                }
            }
        }
    }

    private void loadShipImage() {
        try {
            Image shipImage = new Image(
                    getClass().getResource("/images/ship.png").toExternalForm(),
                    scale, scale, true, true);
            shipImageView = new ImageView(shipImage);
            shipImageView.setX(ship.getX() * scale);
            shipImageView.setY(ship.getY() * scale);
            rootPane.getChildren().add(shipImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startSailing(Scene scene) {
        scene.setOnKeyPressed((KeyEvent ke) -> {
            switch (ke.getCode()) {
                case RIGHT: ship.moveEast(oceanMap); break;
                case LEFT:  ship.moveWest(oceanMap); break;
                case UP:    ship.moveNorth(oceanMap); break;
                case DOWN:  ship.moveSouth(oceanMap); break;
                default: break;
            }
            shipImageView.setX(ship.getX() * scale);
            shipImageView.setY(ship.getY() * scale);
        });
    }

    private void onGameOver() {
        new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            Platform.runLater(() -> {
                gameOverLabel = new Label("GAME OVER! Pirates caught Columbus!");
                gameOverLabel.setTextFill(Color.RED);
                gameOverLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                gameOverLabel.setLayoutX(rootPane.getWidth() / 2 - 180);
                gameOverLabel.setLayoutY(rootPane.getHeight() / 2 - 20);
                rootPane.getChildren().add(gameOverLabel);
            });
        }).start();
    }
}
