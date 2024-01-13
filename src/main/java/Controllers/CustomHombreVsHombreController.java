/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import static Controllers.CustomController.CPUBegin;
import static Controllers.CustomController.esCirculo;
import static Controllers.CustomController.player1Begin;
import static Controllers.CustomController.rdCirculo;
import static Controllers.CustomController.rdCruz;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abeni
 */
public class CustomHombreVsHombreController implements Initializable {

    public static RadioButton rdPl1 = new RadioButton("Player 1");
    public static RadioButton rdPl2 = new RadioButton("Player 2");
    public static RadioButton rd0 = new RadioButton("Circulo");
    public static RadioButton rdX = new RadioButton("Cruz");
    public static boolean pl1Begin = false, pl2Begin = false;
    public static boolean esCircle = false;

    @FXML
    private Button btregreso;
    @FXML
    private ImageView imgP1;
    @FXML
    private ImageView imgP2;
    @FXML
    private HBox hbradioButton;
    @FXML
    private HBox hbOpciones;
    @FXML
    private AnchorPane PaneCustomHvsH;
    @FXML
    private Label lbplayer1;
    @FXML
    private Label lbcomputador;
    @FXML
    private Button btStart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ponerBotones();
        selectIcon();
    }    

    @FXML
    private void volverInicio(ActionEvent event) {
        Platform.runLater(() -> {
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) btregreso.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });
    }

    @FXML
    private void StartGame(ActionEvent event) {
        if (rdPl1.isSelected()) {
            pl1Begin = true;
            pl2Begin = false;
        } else {
            pl2Begin = true;
            pl1Begin = false;
        }
        
        if (rd0.isSelected()) {
            esCircle = true;
        } else {
            esCircle = false;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VsJugador.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ponerBotones() {

        ToggleGroup selectInicio = new ToggleGroup();
        rdPl2.setToggleGroup(selectInicio);
        rdPl1.setToggleGroup(selectInicio);
        
        hbradioButton.getChildren().addAll(rdPl1,rdPl2);
        hbradioButton.setSpacing(20);
        ToggleGroup selectOpcion = new ToggleGroup();
        rd0.setToggleGroup(selectOpcion);
        rdX.setToggleGroup(selectOpcion);
        hbOpciones.getChildren().addAll(rd0,rdX);
        hbOpciones.setSpacing(65);
    }  
    
    public void selectIcon() {
        rd0.setOnMouseClicked(event -> {      
            if (rd0.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgP1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgP2.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
        rdX.setOnMouseClicked(event -> {
            if (rdX.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgP1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgP2.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
    }    
    
}
