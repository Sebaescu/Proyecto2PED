package Controllers;

import static Controllers.CustomController.esCirculo;
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
    public boolean playerTurn = true,deshabilitarBtns = false,isGameOver = false,boardFull =false;
    
    
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
    @FXML
    private Label lblRcmd;
    @FXML
    private Label lbsimbolP1;
    @FXML
    private Label lbsimboloCPU;



@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblRcmd.setVisible(false);
        if(esCirculo){
            lbsimbolP1.setText("O");
            lbsimboloCPU.setText("X");
        }else{
            lbsimbolP1.setText("X");
            lbsimboloCPU.setText("O");
        }
        
        txscoreP1.setText(String.valueOf(contPlayer1));
        txscoreCPU.setText(String.valueOf(contCPU));
        
        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
        if(CustomController.CPUBegin){
            makeAIMove();
            recomendarJugada();
        } 

    }

    @FXML
    void restartGame(ActionEvent event) {
        lblRcmd.setVisible(false);

        if (CustomController.CPUBegin) {
            buttons.forEach(this::resetButton);
            winnerText.setText("Tic-Tac-Toe");
            deshabilitarBtns = false;
            isGameOver = false;
            pickButton(random.nextInt(9));
            recomendarJugada();
        } else if (CustomController.player1Begin){
            buttons.forEach(this::resetButton);
            winnerText.setText("Tic-Tac-Toe");
            deshabilitarBtns = false;
            isGameOver = false;
        }    
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

    private void setupButton(Button button) {
        if (esCirculo) {
            button.setOnMouseClicked(mouseEvent -> {
             
                button.setText("O");
                button.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
                pause.setOnFinished(event -> {
                    checkIfGameIsOver();
                    if (isGameOver == false) {
                        makeAIMove();
                        checkIfGameIsOver();
                    }
                });
                pause.play();

            });

        } else {
            button.setOnMouseClicked(mouseEvent -> {
                    button.setText("X");
                    button.setDisable(true);
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.25));
                    pause.setOnFinished(event -> {
                        checkIfGameIsOver();
                        if (isGameOver == false) {
                            makeAIMove();
                            checkIfGameIsOver();
                        }

                    });
                    pause.play();
                });
            }

    }

    public void makeAIMove(){
        lblRcmd.setText("");
        if (playerTurn) {
            int move = ticTacToeAI.minMaxDecision(this.getBoardState(), "X");
            pickButton(move);
        } else {
            int move = ticTacToeAI.minMaxDecisionTwo(this.getBoardState());
            pickButton(move);
        }
        playerTurn = !playerTurn;
        recomendarJugada();
    }

    public void pickButton(int index) {
        if (index >= 0 && index < buttons.size()) {
            if (esCirculo) {
                buttons.get(index).setText("X");
                buttons.get(index).setDisable(true);
            } else {
                buttons.get(index).setText("O");
                buttons.get(index).setDisable(true); 
            }
        }
        checkIfGameIsOver();
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

            if (esCirculo) {
                if (line.equals("XXX")) {
                    winnerText.setText("Computadora Gana!");  
                    contCPU = contCPU +1;
                    txscoreCPU.setText(String.valueOf(contCPU));
                    deshabilitarBtns = true;
                } else if (line.equals("OOO")) {
                    winnerText.setText("Player 1 Gana!");
                    contPlayer1++;
                    txscoreP1.setText(String.valueOf(contPlayer1));
                    deshabilitarBtns = true;
                }

            } else {
                if (line.equals("XXX")) {
               
                    winnerText.setText("Player 1 Gana!");
                    contPlayer1++;
                    txscoreP1.setText(String.valueOf(contPlayer1));
                    deshabilitarBtns = true;
                } else if (line.equals("OOO")) {
                    winnerText.setText("Computadora Gana!");
                    contCPU = contCPU +1;
                    txscoreCPU.setText(String.valueOf(contCPU));
                    deshabilitarBtns = true;
                }

            }

        }
        
        //verificar empate
        if ((isGameOver == false) && (isBoardFull()) && (winnerText.getText().equals("Player 1 Gana!"))) {
            winnerText.setText("Player 1 Gana!");
        }else if ((isGameOver == false) && (isBoardFull()) && (winnerText.getText().equals("Computadora Gana!"))){
            winnerText.setText("Computadora Gana!");
        }else if ((isGameOver == false) && (isBoardFull())){
            winnerText.setText("Empate");
        }
        
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
       
    public void recomendarJugada(){
        if(isGameOver || isBoardFull() ){
            return;
        }
        int recomendada = ticTacToeAI.minMaxDecisionTwo(this.getBoardState());
            recomendada++;
            lblRcmd.setText("Jugada Recomendada, en casilla: " + recomendada);
            lblRcmd.setVisible(true);
    }    


}


//public class VsComputadorController implements Initializable {
//    
//    int contPlayer1 = 0;
//    int contCPU = 0;
//    Random random = new Random();
//    ArrayList<Button> buttons;
//    MinMax ticTacToeAI = new MinMax();
//    public boolean playerTurn = true,deshabilitarBtns = false,isGameOver = false,boardFull = false;
//
//    @FXML
//    private Button button1;
//    @FXML
//    private Button button2;
//    @FXML
//    private Button button3;
//    @FXML
//    private Button button4;
//    @FXML
//    private Button button5;
//    @FXML
//    private Button button6;
//    @FXML
//    private Button button7;
//    @FXML
//    private Button button8;
//    @FXML
//    private Button button9;
//    @FXML
//    private Text winnerText;
//    @FXML
//    private FlowPane PaneTableroIA;
//    @FXML
//    private Label lbTitulo;
//    @FXML
//    private Label lbresult;
//    @FXML
//    private Label lblRcmd;
//    @FXML
//    private TextField txscoreP1;
//    @FXML
//    private TextField txscoreCPU;
//
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        lblRcmd.setVisible(false);
//        txscoreP1.setText(String.valueOf(contPlayer1));
//        txscoreCPU.setText(String.valueOf(contCPU));
//        
//        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));
//
//        buttons.forEach(button ->{
//            setupButton(button);
//            button.setFocusTraversable(false);
//        });
//        if(CustomController.CPUBegin){
//            makeAIMove();
//            recomendarJugada();
//        } 
//
//    }
//
//    @FXML
//    void restartGame(ActionEvent event) {
//        if (CustomController.CPUBegin) {
//            buttons.forEach(this::resetButton);
//            winnerText.setText("");
//            deshabilitarBtns = false;
//            isGameOver = false;
//            boardFull = false;
//            lblRcmd.setVisible(false);
//            pickButton(random.nextInt(9));
//            recomendarJugada();
//        } else if (CustomController.player1Begin){
//            buttons.forEach(this::resetButton);
//            winnerText.setText("");
//            deshabilitarBtns = false;
//            isGameOver = false;
//            boardFull = false;
//            lblRcmd.setVisible(false);
//        }
//            
//    }
//
//
//    public void resetButton(Button button){
//        button.setDisable(false);
//        button.setText("");
//    }
//    @FXML
//    private void volverAlMenu() {
//        Platform.runLater(() -> {
//            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
//            Stage stage = (Stage) button1.getScene().getWindow();
//            menuPrincipal.mostrarMenuPrincipal(stage);
//        });
//    }
//    
//    // este metodo me permite elegir que signo soy yo
//    public void setupButton(Button button) {
//        if (esCirculo) {
//            button.setOnMouseClicked(mouseEvent -> {
//                button.setText("O");
//                button.setDisable(true);
//
//                // Agrega un retraso antes de que la computadora realice su movimiento
//                PauseTransition pause = new PauseTransition(Duration.seconds(0.15));
//                pause.setOnFinished(event -> {
//                    checkIfGameIsOver();
//                    if (isGameOver == false) {
//                        makeAIMove();
//                        checkIfGameIsOver();
//                    }
//                });
//                pause.play();
//            });
//
//        } else {
//            button.setOnMouseClicked(mouseEvent -> {
//                    button.setText("X");
//                    button.setDisable(true);
//                    PauseTransition pause = new PauseTransition(Duration.seconds(0.15));
//                    pause.setOnFinished(event -> {
//                        checkIfGameIsOver();
//                        if (isGameOver == false) {
//                            makeAIMove();
//                            checkIfGameIsOver();
//                        }
//
//                    });
//                    pause.play();
//                });
//            }
//
//    }
//    public void makeAIMove(){
//        lblRcmd.setText("");
//        int move = ticTacToeAI.minMaxDecision(getBoardState());
//        pickButton(move);
//        if(isGameOver == false && boardFull ==false){
//            recomendarJugada();
//        }
//    }
//
//    //este metodo me elige que signo sera la computadora    
//    public void pickButton(int index) {
//        if (index >= 0 && index < buttons.size()) {
//            if (esCirculo) {
//                buttons.get(index).setText("X");
//                buttons.get(index).setDisable(true);
//            } else {
//                buttons.get(index).setText("O");
//                buttons.get(index).setDisable(true); 
//            }
//        }
//    }
//
//    public State getBoardState(){
//        String[] board = new String[9];
//
//        for (int i = 0; i < buttons.size(); i++) {
//            board[i] = buttons.get(i).getText();
//        }
//
//        return new State(0,board);
//    }
//    public void recomendarJugada(){
//        int recomendada = ticTacToeAI.minMaxDecision(getBoardState());
//            recomendada++;
//            lblRcmd.setText("Jugada Recomendada: " + recomendada);
//            lblRcmd.setVisible(true);
//    }
//
//    public void checkIfGameIsOver(){
//        String line = null;
//        
//        for (int a = 0; a < 8; a++) {
//            line = switch (a) {
//                case 0 -> button1.getText() + button2.getText() + button3.getText();
//                case 1 -> button4.getText() + button5.getText() + button6.getText();
//                case 2 -> button7.getText() + button8.getText() + button9.getText();
//                case 3 -> button1.getText() + button5.getText() + button9.getText();
//                case 4 -> button3.getText() + button5.getText() + button7.getText();
//                case 5 -> button1.getText() + button4.getText() + button7.getText();
//                case 6 -> button2.getText() + button5.getText() + button8.getText();
//                case 7 -> button3.getText() + button6.getText() + button9.getText();
//                default -> null;
//            };
//            
//             if (line.equals("XXX")) {
//                if(CustomController.esCirculo == true){
//                    winnerText.setText("Computadora Gana!");
//                    contCPU++;
//                }else {
//                    winnerText.setText("Player 1 Gana!");
//                    contPlayer1++;
//                }   
//                isGameOver = true;
//                txscoreCPU.setText(String.valueOf(contCPU));
//                txscoreP1.setText(String.valueOf(contPlayer1));
//                deshabilitarBtns = true;              
//             }else if (line.equals("OOO")) {
//                if(CustomController.esCirculo == true){
//                    winnerText.setText("Player 1 Gana!");
//                    contPlayer1++;
//                }else{
//                    winnerText.setText("Computadora Gana!");
//                    contCPU++;
//                }
//                isGameOver = true;             
//                txscoreP1.setText(String.valueOf(contPlayer1));
//                txscoreCPU.setText(String.valueOf(contCPU));
//                deshabilitarBtns = true;   
//            }
//
//        }
//
//        if ((isGameOver == false) && (isBoardFull())){
//            winnerText.setText("Empate");
//        }
//        
//        if(deshabilitarBtns){
//            isGameOver = true;
//            buttons.forEach(button ->{
//                button.setDisable(true);
//            });
//            lblRcmd.setVisible(false);
//        }
//    }
//
//    public boolean isBoardFull() {
//        for (Button button : buttons) {
//            if (button.getText().isEmpty()) {
//                boardFull = false;
//                return false; 
//            }
//        }
//        boardFull = true;
//        return true;
//    }
//    
//}


/*
//Clase de Respaldo con la version Anterior del algoritmo


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
            winnerText.setText("");
            deshabilitarBtns = false;
            isGameOver = false;
            pickButton(random.nextInt(9));
        } else if (CustomController.player1Begin){
            buttons.forEach(this::resetButton);
            winnerText.setText("");
            deshabilitarBtns = false;
            isGameOver = false;
        }
            
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
    
    // este metodo me permite elegir que signo soy yo
    public void setupButton(Button button) {
        if (esCirculo) {
            button.setOnMouseClicked(mouseEvent -> {
                button.setText("O");
                button.setDisable(false);// aqui era true, pero yo le cambie a false

                // Agrega un retraso antes de que la computadora realice su movimiento
                PauseTransition pause = new PauseTransition(Duration.seconds(0.15));
                pause.setOnFinished(event -> {
                    checkIfGameIsOver();
                    if (!isGameOver) {
                        makeAIMove();
                        checkIfGameIsOver();
                    }

                });
                pause.play();
            });

        } else {
            button.setOnMouseClicked(mouseEvent -> {
                    button.setText("X");
                    button.setDisable(false);// aqui era true, pero yo le cambie a false
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.15));
                    pause.setOnFinished(event -> {
                        checkIfGameIsOver();
                        if (!isGameOver) {
                            makeAIMove();
                            checkIfGameIsOver();
                        }

                    });
                    pause.play();
                });
            }

    }
    public void makeAIMove(){
//        if (!isGameOver) {
//            int move = ticTacToeAI.minMaxDecision(getBoardState());
//            pickButton(move);
//        }
        int move = ticTacToeAI.minMaxDecision(getBoardState());
        pickButton(move);
    }

    //este metodo me elige que signo sera la computadora    
    public void pickButton(int index) {
        if (esCirculo) {
            buttons.get(index).setText("X");
            buttons.get(index).setDisable(false); // aqui era true, pero le cambie a false
        } else {
            buttons.get(index).setText("O");
            buttons.get(index).setDisable(false); // aqui era true, pero le cambie a false
        }
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
            
            if (line.equals("XXX") && esCirculo == true) {
                winnerText.setText("Computadora Gana!");
                contCPU++;
                txscoreCPU.setText(String.valueOf(contCPU));
                isGameOver = true;
                deshabilitarBtns = true;              
            }else if (line.equals("XXX") && esCirculo == false) {
                winnerText.setText("Player 1 Gana!");
                contPlayer1++;
                txscoreP1.setText(String.valueOf(contPlayer1));
                isGameOver = true;
                deshabilitarBtns = true;              
            }else if (line.equals("OOO") && esCirculo == true) {
                winnerText.setText("Player 1 Gana!");
                contPlayer1++;
                txscoreP1.setText(String.valueOf(contPlayer1));
                isGameOver = true;
                deshabilitarBtns = true;   
            }else if (line.equals("OOO") && esCirculo == false) {
                winnerText.setText("Computadora Gana!");
                contCPU++;
                txscoreCPU.setText(String.valueOf(contCPU));
                isGameOver = true;
                deshabilitarBtns = true;   
            }

        }

        if ((isGameOver == false) && (isBoardFull())){
            winnerText.setText("Empate");
        }
        
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
*/

