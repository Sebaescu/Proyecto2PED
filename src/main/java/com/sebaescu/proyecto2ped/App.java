package com.sebaescu.proyecto2ped;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {

    private static final int GRID_SIZE = 3;
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    private char currentPlayer = PLAYER_X;
    private char[][] board = new char[GRID_SIZE][GRID_SIZE];
    private Label label1,label2;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        MainMenu menuPrincipal = new MainMenu();
        menuPrincipal.start(primaryStage);
    }
    
    public void startJuego(Stage primaryStage) {
        // Crea el VBox para los labels
        this.stage = primaryStage;
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.setAlignment(Pos.CENTER);

        // Crea los labels
        label1 = new Label("3 En Raya");
        label1.setFont(new Font(20)); // Aumenta el tamaño del texto
        vbox.getChildren().add(label1);

        label2 = new Label("Turno jugador 1 (X)");
        vbox.getChildren().add(label2);

        // Crea la cuadrícula del juego
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        grid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Si hay un ganador, deshabilita todos los botones
                if (checkWinner()) {
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            grid.getChildren().get(i * GRID_SIZE + j).setDisable(true);
                        }
                    }
                }
            }
        });

        // Crea los botones del juego
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Button button = new Button();
                button.setMinWidth(100);
                button.setMinHeight(100);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        handleButtonClick(button);
                    }
                });
                grid.add(button, i, j);
            }
        }

        // Añade el VBox y la cuadrícula a la escena
        vbox.getChildren().add(grid);
        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tres en raya");
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        // Obtiene la fila y columna del botón
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        // Verifica si el botón ya está ocupado
        if (board[row][col] != '\0') {
            return;
        }

        // Coloca la ficha del jugador actual en el tablero
        board[row][col] = currentPlayer;

        // Muestra la ficha en el botón
        if (currentPlayer == PLAYER_X) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        // Cambia el jugador actual
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;

        // Actualiza el label del turno
        if (currentPlayer == PLAYER_X) {
            label2.setText("Turno jugador 1 (X)");
        } else {
            label2.setText("Turno jugador 2 (O)");
        }

        // Verifica si hay un ganador
        if (checkWinner()) {
            // Cambia al jugador que gano
            currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
            // Muestra un mensaje de victoria
            mostrarMensaje("¡El jugador " + currentPlayer + " ha ganado!");      
        }
        if (checkDraw()) {
            // Cambia al jugador que gano
            // Muestra un mensaje de victoria
            mostrarMensaje("Nadie ganó!");      
        }
    }
    
    private boolean checkWinner() {
        // Verifica si hay tres fichas del mismo jugador en una fila
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '\0') {
                return true;
            }
        }

        // Verifica si hay tres fichas del mismo jugador en una columna
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '\0') {
                return true;
            }
        }

        // Verifica si hay tres fichas del mismo jugador en una diagonal
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\0') {
            return true;
        }
        // Si no hay ganador, retorna false

        return board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\0';
    }
    boolean checkDraw() {
        // Verifica si todos los cuadrados están marcados
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        // Verifica si hay un ganador
        // Si no hay ganador y todos los cuadrados están marcados, entonces hay un empate

        return !checkWinner();
    }
    private void mostrarMensaje(String mensaje) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (checkDraw()) {
                alert.setTitle("¡Empate!");     
            }
            if (checkWinner()) {
                alert.setTitle("¡Ganaste!");     
            }
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.setOnHidden(event -> volverAlMenu());
            alert.show();
        });
    }
    private void volverAlMenu() {
        MainMenu menuPrincipal = new MainMenu();
        menuPrincipal.start(stage);
    }
    public static void main(String[] args) {
    // Lanza la aplicación
    Application.launch(App.class, args);
    }   
}