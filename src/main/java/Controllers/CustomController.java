/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import static Controllers.MenuPrincipalController.primaryStage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomController implements Initializable {

    public static RadioButton rdP1 = new RadioButton("Player 1");
    public static RadioButton rdCPU = new RadioButton("Computador");
    public static RadioButton rdCirculo = new RadioButton("Circulo");
    public static RadioButton rdCruz = new RadioButton("Cruz");
    public static boolean player1Begin = false,CPUBegin = false;
    public static boolean esCirculo = false;

    
    @FXML
    private AnchorPane PaneCustom;
    @FXML
    private Button btHome;
    @FXML
    private Label lbplayer1;
    @FXML
    private Label lbcomputador;
    @FXML
    private ImageView imgplayer1;
    @FXML
    private ImageView imgCPU;
    @FXML
    private Button btIniciar;
    @FXML
    private HBox hbradioButton,hbOpciones;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ponerBotones();      
    }    

    @FXML
    private void volverInicio(ActionEvent event) {
        Platform.runLater(() -> {
            // Crea una nueva instancia del MainMenu y muestra la ventana maximizada
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) btHome.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });
    }

    @FXML
    private void comenzarJuego(ActionEvent event) {
        
        if(rdP1.isSelected()){
            player1Begin = true;
            CPUBegin = false;
        }else{
            CPUBegin = true;
            player1Begin = false;
        }
        if(rdCirculo.isSelected()){
            esCirculo = true;
        }else{
            esCirculo = false;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VsComputador.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ponerBotones() {

        ToggleGroup selectInicio = new ToggleGroup();
        rdCPU.setToggleGroup(selectInicio);
        rdP1.setToggleGroup(selectInicio);
        
        hbradioButton.getChildren().addAll(rdP1,rdCPU);
        hbradioButton.setSpacing(20);
        ToggleGroup selectOpcion = new ToggleGroup();
        rdCirculo.setToggleGroup(selectOpcion);
        rdCruz.setToggleGroup(selectOpcion);
        hbOpciones.getChildren().addAll(rdCirculo,rdCruz);
        hbOpciones.setSpacing(65);
    }


}
