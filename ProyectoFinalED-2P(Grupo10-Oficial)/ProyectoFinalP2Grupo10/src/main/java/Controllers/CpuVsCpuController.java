package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import tictactoe.MinMax;
import tictactoe.State;

public class CpuVsCpuController implements Initializable {
    private int playerTurn = 0;
    int contCPU2 = 0;
    int contCPU = 0;
    private int startingComputer = 1;
    ArrayList<Button> buttons;
    MinMax ticTacToeAI = new MinMax();
    public boolean deshabilitarBtns = false, isGameOver = false;

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Text winnerText;
    @FXML
    private TextField txtscoreCPU,txtscoreCPU2;
    @FXML
    private FlowPane PaneTableroIA;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbresult;
    @FXML
    private Label labelJugador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(
            button1, button2, button3, button4, button5, button6, button7, button8, button9));

        buttons.forEach(button -> button.setFocusTraversable(false));

        updateLabelJugadorText();
        makeAIMove();
        txtscoreCPU.setText(String.valueOf(contCPU));
        txtscoreCPU2.setText(String.valueOf(contCPU2));
    }
    
    private void updateLabelJugadorText() {
        labelJugador.setText("Inicia la partida la Maquina " + startingComputer);
    }

    public void makeAIMove() {
        if (!isGameOver) {
            int move = ticTacToeAI.minMaxDecisionTwo(getBoardState());
            pickButton(move);
            setPlayerSymbol();
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                checkIfGameIsOver();
                makeAIMove();
            });
            pause.play();
        }
    }

    public void setPlayerSymbol() {
        if(playerTurn % 2 == 0){
            playerTurn = 1;
        } else {
            playerTurn = 0;
        }
    }

    public void pickButton(int index) {
        if (index >= 0 && index < buttons.size()) {
            buttons.get(index).setText(playerTurn % 2 == 0 ? "X" : "O");
            buttons.get(index).setDisable(false); 
        }
    }

    public State getBoardState() {
        String[] board = new String[9];

        for (int i = 0; i < buttons.size(); i++) {
            board[i] = buttons.get(i).getText();
        }

        return new State(0, board);
    }

    public void checkIfGameIsOver() {
        String line = null;
        boolean gameOver = false;
        for (int a = 0; a < 8; a++) {
            line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            if (line.equals("XXX")) {
                winnerText.setText("Computadora 1 Gana!");
                contCPU++;
                isGameOver = true;
                gameOver = true;
                txtscoreCPU.setText(String.valueOf(contCPU));               
                deshabilitarBtns = true;
            } else if (line.equals("OOO")) {
                winnerText.setText("Computadora 2 Gana!");
                contCPU2++;
                isGameOver = true;
                gameOver = true;
                txtscoreCPU2.setText(String.valueOf(contCPU2));                
                deshabilitarBtns = true;
            }
        }
        
        if (!gameOver && isBoardFull()) {
        winnerText.setText("Empate");
        isGameOver = true;
        }

        if (isGameOver) {
            deshabilitarBtns = true;
            buttons.forEach(button -> button.setDisable(true));
        }

    }

    public boolean isBoardFull() {
        for (Button button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
        deshabilitarBtns = false;
        isGameOver = false;
        startingComputer = 3 - startingComputer; 
        updateLabelJugadorText();
        makeAIMove(); 
    }

    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }

    @FXML
    private void volverAlMenu() {
        Platform.runLater(() -> {
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) button1.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });
    }
}
