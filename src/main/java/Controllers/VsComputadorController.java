package Controllers;

import static Controllers.CustomController.player1Begin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import tictactoe.MinMax;
import tictactoe.State;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


public class VsComputadorController implements Initializable {
    
    int contPlayer1 = 0;
    int contCPU = 0;
    Random random = new Random();
    ArrayList<Button> buttons;
    MinMax ticTacToeAI = new MinMax();
    public boolean playerTurn = true,deshabilitarBtns = false,isGameOver = false;

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
    private FlowPane PaneTableroIA;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbresult;
    @FXML
    private TextField txscoreP1;
    @FXML
    private TextField txscoreCPU;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txscoreP1.setText(String.valueOf(contPlayer1));
        txscoreCPU.setText(String.valueOf(contCPU));
        
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
        if(CustomController.CPUBegin){
            makeAIMove();
        } 

    }

    @FXML
    void restartGame(ActionEvent event) {
        if (CustomController.CPUBegin) {
            buttons.forEach(this::resetButton);
            winnerText.setText("Tic-Tac-Toe");
            deshabilitarBtns = false;
            isGameOver = false;
            pickButton(random.nextInt(9));
        } else if (CustomController.player1Begin){
            buttons.forEach(this::resetButton);
            winnerText.setText("Tic-Tac-Toe");
            deshabilitarBtns = false;
            isGameOver = false;
        }
        
//        buttons.forEach(this::resetButton);
//        winnerText.setText("Tic-Tac-Toe");
//        deshabilitarBtns = false;
//        isGameOver = false;
//        pickButton(random.nextInt(9));
//        if(CustomController.CPUBegin){
//            makeAIMove();
//        }      
    }


    public void resetButton(Button button){
        button.setDisable(false);
        button.setText("");
    }
    @FXML
    private void volverAlMenu() {
        Platform.runLater(() -> {
            // Crea una nueva instancia del MainMenu y muestra la ventana maximizada
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) button1.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            button.setText("O");
            button.setDisable(true);

            // Agrega un retraso antes de que la computadora realice su movimiento
            PauseTransition pause = new PauseTransition(Duration.seconds(0.15));
            pause.setOnFinished(event -> {
                checkIfGameIsOver(); 
                if(!isGameOver){
                    makeAIMove();
                    checkIfGameIsOver(); 
                }
                       
            });
            pause.play();
        });
    }

    public void makeAIMove(){
        
        int move = ticTacToeAI.minMaxDecision(getBoardState());
        pickButton(move);
    }

    public void pickButton(int index) {
        buttons.get(index).setText("X");
        buttons.get(index).setDisable(true);
    }

    public State getBoardState(){
        String[] board = new String[9];

        for (int i = 0; i < buttons.size(); i++) {
            board[i] = buttons.get(i).getText();
        }

        return new State(0,board);
    }

    public void checkIfGameIsOver(){
        String line = null;
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
                winnerText.setText("Computadora Gana!");
                contCPU++;
                txscoreCPU.setText(String.valueOf(contCPU));
                deshabilitarBtns = true;              
            }

            else if (line.equals("OOO")) {
                winnerText.setText("Player 1 Gana!");
                contPlayer1++;
                txscoreP1.setText(String.valueOf(contPlayer1));
                deshabilitarBtns = true;   
            }

        }
        
        // esta manera junto con lo de abajo funcionan de la misma forma 
        // pero se presenta ese problema que indique en el video
        if (!isGameOver && isBoardFull()) {
            switch (line) {
                case "XXX":
                    winnerText.setText("Computadora Gana!");
                    break;
                case "OOO":
                    winnerText.setText("Player 1 Gana!");
                    break;
                default:
                    winnerText.setText("Empate");
                    deshabilitarBtns = true;
                    break;
            }

        }
    
//        if (!isGameOver && isBoardFull()) {
//            if (line.equals("XXX")) {
//                winnerText.setText("Computadora Gana!");          
//            }
//            else if (line.equals("OOO")) {
//                winnerText.setText("Player 1 Gana!");
//  
//            }else{
//                winnerText.setText("Empate");
//                deshabilitarBtns = true;
//            }
//              
//        }
        
        if(deshabilitarBtns){
            isGameOver = true;
            buttons.forEach(button ->{
                button.setDisable(true);
            });
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
}
