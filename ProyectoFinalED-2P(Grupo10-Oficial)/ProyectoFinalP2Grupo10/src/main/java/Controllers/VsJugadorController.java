/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abeni
 */
public class VsJugadorController implements Initializable {
    
    public boolean isGameOver = false;

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

    private int playerTurn = 0;
    private boolean gameWon = false;
    int contP1 = 0;
    int contP2 = 0;
    public boolean deshabilitarBtns = false;
    ArrayList<Button> buttons;
    @FXML
    private Label lbjugador1;
    @FXML
    private Label lbjugador2;
    @FXML
    private TextField txscoreP1,txscoreP2;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbresult;
    @FXML
    private Label lbjugador11;
    @FXML
    private Label lbjugador111;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        buttons = new ArrayList<>(Arrays.asList(button1,button2,button3,button4,button5,button6,button7,button8,button9));

        buttons.forEach(button ->{
            setupButton(button);
            button.setFocusTraversable(false);
        });
        txscoreP1.setText(String.valueOf(contP1));
        txscoreP2.setText(String.valueOf(contP2));
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("");
        gameWon = false;  
        deshabilitarBtns = false;
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
    public void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (!gameWon && !deshabilitarBtns) {
                setPlayerSymbol(button);
                button.setDisable(true); // pilas aqui yo le puse false en lugar de true
                checkIfGameIsOver();
            }
        });        
    }

    public void setPlayerSymbol(Button button){
        if (!gameWon && !deshabilitarBtns) {
            if (playerTurn % 2 == 0) {
                button.setText("X");
                playerTurn = 1;
            } else {
                button.setText("O");
                playerTurn = 0;
            }
        }        

    }

    public void checkIfGameIsOver(){
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

            //X winner
            if (line.equals("XXX")) {
                gameWon = true;
                winnerText.setText("Player 1 Gano!!");
                contP1++;
                txscoreP1.setText(String.valueOf(contP1));
            }

            //O winner
            else if (line.equals("OOO")) {
                gameWon = true;
                winnerText.setText("Player 2 Gano!!");
                contP2++;
                txscoreP2.setText(String.valueOf(contP2));
            }

        }
        
        //verificar empate
        if ((isGameOver == false) && (isBoardFull()) && (winnerText.getText().equals("Player 1 Gano!!"))) {
            winnerText.setText("Player 1 Gano!!");
        }else if ((isGameOver == false) && (isBoardFull()) && (winnerText.getText().equals("Player 2 Gano!!"))){
            winnerText.setText("Player 2 Gano!!");
        }else if ((isGameOver == false) && (isBoardFull())){
            winnerText.setText("Empate");
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

    public boolean allButtonsDisabled() {
        return buttons.stream().allMatch(button -> button.isDisabled());
    }



}
