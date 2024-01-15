/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import static Controllers.MenuPrincipalController.primaryStage;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CustomController implements Initializable {

    public static RadioButton rdJugador1 = new RadioButton("Player 1");
    public static RadioButton rdCPU = new RadioButton("Computador");
    public static RadioButton rdCirculo = new RadioButton("Circulo");
    public static RadioButton rdCruz = new RadioButton("Cruz");
    public Font customFont = new Font("Rockwell", 18);
    public static boolean player1Begin = false,CPUBegin = false;
    public static boolean esCirculo = false;

    
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
    private HBox hbradioButton,hbOpciones;
    @FXML
    private AnchorPane PaneCustom;
    @FXML
    private Button btIniciar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ponerBotones();
        rdJugador1.setStyle("-fx-text-fill: #00EDD0; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 0, 0, 1);");
        rdCPU.setStyle("-fx-text-fill: #00EDD0; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 0, 0, 1);");
        rdCirculo.setStyle("-fx-text-fill: #00EDD0; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 0, 0, 1);");
        rdCruz.setStyle("-fx-text-fill: #00EDD0; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 0, 0, 1);");
        seleccionarFicha();
        
    } 

    @FXML
    private void volverInicio(ActionEvent event) {
        Platform.runLater(() -> {
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) btHome.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });
    }

    @FXML
    public void comenzarJuego(ActionEvent event) {
        
        if(rdJugador1.isSelected()){
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
        rdJugador1.setToggleGroup(selectInicio);
        rdJugador1.setFont(customFont);
        rdCPU.setFont(customFont);
        
        hbradioButton.getChildren().addAll(rdJugador1,rdCPU);
        hbradioButton.setSpacing(20);
        ToggleGroup selectOpcion = new ToggleGroup();
        rdCirculo.setToggleGroup(selectOpcion);
        rdCruz.setToggleGroup(selectOpcion);
        rdCirculo.setFont(customFont);
        rdCruz.setFont(customFont);
                
        hbOpciones.getChildren().addAll(rdCirculo,rdCruz);
        hbOpciones.setSpacing(45);
    }
 
    public void seleccionarFicha() {
        rdCirculo.setOnMouseClicked(event -> {
            if (rdCirculo.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgplayer1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
        rdCruz.setOnMouseClicked(event -> {
            if (rdCruz.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgplayer1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
    }



}
