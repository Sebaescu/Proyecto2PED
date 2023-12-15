/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sebaescu.proyecto2ped;

/**
 *
 * @author Sebastian
 */
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #b3b3b3;");

        Label title = new Label("3 En Rayas");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        Button startButton = new Button("Player vs Player");
        Button AIButton = new Button("Player vs Computer");
        Button closeButton = new Button("Close");

        HBox buttonsBox = new HBox(40, AIButton,startButton, closeButton);
        buttonsBox.setAlignment(Pos.CENTER);

        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setAlignment(buttonsBox, Pos.CENTER);
        StackPane.setMargin(title, new javafx.geometry.Insets(50, 0, 0, 0));
        StackPane.setMargin(buttonsBox, new javafx.geometry.Insets(50, 0, 0, 0));

        root.getChildren().addAll(title, buttonsBox);

        Scene scene = new Scene(root, 400, 400);

        startButton.setOnAction(event -> {
            primaryStage.hide();
            App game = new App();
            game.startJuego(new Stage());
        });

        closeButton.setOnAction(event -> primaryStage.close());

        primaryStage.setTitle("ExploCaves");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
