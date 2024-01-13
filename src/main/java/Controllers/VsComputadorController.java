package Controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.JFrame;
import javax.swing.JLabel;


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
            if(CustomController.esCirculo == true){
                button.setText("O");
            }else{
                button.setText("X");
            }
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
        if(isGameOver == false && isBoardFull() == false ){
            PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
                pause.setOnFinished(event -> {
                    mostrarPopUp("Jugada Recomendada: "+ recommendMove());
                });
                pause.play();
        }
    }

    public void pickButton(int index) {
        if(CustomController.esCirculo == true){
            buttons.get(index).setText("X");
        }else{
            buttons.get(index).setText("O");
        }
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
                if(CustomController.esCirculo == true){
                    winnerText.setText("Computadora Gana!");
                    contCPU++;
                }else{
                    winnerText.setText("Player 1 Gana!");
                    contPlayer1++;
                }   
                isGameOver = true;
                txscoreCPU.setText(String.valueOf(contCPU));
                txscoreP1.setText(String.valueOf(contPlayer1));
                deshabilitarBtns = true;              
            }

            else if (line.equals("OOO")) {
                if(CustomController.esCirculo == true){
                    winnerText.setText("Player 1 Gana!");
                    contPlayer1++;
                }else{
                    winnerText.setText("Computadora Gana!");
                    contCPU++;
                }
                isGameOver = true;             
                txscoreP1.setText(String.valueOf(contPlayer1));
                txscoreCPU.setText(String.valueOf(contCPU));
                deshabilitarBtns = true;   
            }
        }
        
        if (isGameOver == false && isBoardFull()) {
            switch (line) {
                case "XXX":
                    winnerText.setText("Computadora Gana!");
                    break;
                case "OOO":
                    winnerText.setText("Player 1 Gana!");
                    break;
                default:
                    isGameOver = true;
                    winnerText.setText("Empate");
                    deshabilitarBtns = true;
                    break;
            }
        }         
        if(deshabilitarBtns){
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
    public int recommendMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().isEmpty()) {
                buttons.get(i).setText("O");

                int score = minimax(false, 0);

                buttons.get(i).setText(""); // Deshacer la jugada

                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }
    
    public int minimax(boolean isMaximizing, int depth) {
        String result = checkWinner();
        if (result.equals("X")) {
            return -1;
        } else if (result.equals("O")) {
            return 1;
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().isEmpty()) {
                    buttons.get(i).setText("O");
                    int score = minimax(false, depth + 1);
                    buttons.get(i).setText(""); // Deshacer la jugada
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().isEmpty()) {
                    buttons.get(i).setText("X");
                    int score = minimax(true, depth + 1);
                    buttons.get(i).setText(""); // Deshacer la jugada
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
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
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }
        return "";
    }
    public static void mostrarPopUp(String mensaje) {
        // Crear una nueva ventana emergente
        JFrame frame = new JFrame();
        frame.setSize(250, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true); // Sin bordes ni botones de cierre
        frame.getContentPane().setBackground(Color.decode("#6e9167")); // Color de fondo  
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.decode("#fcf3e3")); // Color del texto
        label.setFont(new Font("Arial", Font.PLAIN, 18)); // Puedes ajustar la fuente y el tamaño
        label.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(label);
        

        // Obtener las dimensiones de la pantalla para centrar la ventana emergente
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        int x = ((dim.width - frame.getWidth()) / 2) + 200;
        int y = ((dim.height - frame.getHeight()) / 2) + 100; // En la parte superior
        frame.setLocation(x, y);
        // Mostrar la ventana emergente
        frame.setVisible(true);

        // Esperar la duración especificada
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cerrar la ventana emergente después de la duración especificada
        frame.dispose();
    }
}
