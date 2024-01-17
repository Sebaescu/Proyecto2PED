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

/**
 * FXML Controller class
 *
 * @author Abeni
 */
public class CustomMaquinaVsMaquinaController implements Initializable {

    public static RadioButton rdCp1 = new RadioButton("Maquina 1");
    public static RadioButton rdCp2 = new RadioButton("Maquina 2");
    public static RadioButton rdCR = new RadioButton("Circulo");
    public static RadioButton rdCX = new RadioButton("Cruz");
    public Font tipoLetter = new Font("Rockwell", 18);
    public static boolean cp1Begin = false, cp2Begin = false;
    public static boolean esO = false;    

    @FXML
    private AnchorPane PaneCustomMvsM;
    @FXML
    private Button btReturn;
    @FXML
    private Label lbCPU1;
    @FXML
    private Label lbCPU2;
    @FXML
    private ImageView imgCPU1;
    @FXML
    private ImageView imgCPU2;
    @FXML
    private Button btGo;
    @FXML
    private HBox hbradioButton;
    @FXML
    private HBox hbOpciones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ponerBotones();
        seleccionarFichasJugadores();
    }    

    @FXML
    private void ReturnHome(ActionEvent event) {
        Platform.runLater(() -> {
            MenuPrincipalController menuPrincipal = new MenuPrincipalController();
            Stage stage = (Stage) btReturn.getScene().getWindow();
            menuPrincipal.mostrarMenuPrincipal(stage);
        });        
    }

    @FXML
    private void Gogame(ActionEvent event) {
        if (rdCp1.isSelected()) {
            cp1Begin = true;
            cp2Begin = false;
        } else {
            cp2Begin = true;
            cp1Begin = false;
        }
        
        if (rdCR.isSelected()) {
            esO = true;
        } else {
            esO = false;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CpuVsCpu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
    public void ponerBotones() {

        ToggleGroup selectInicio = new ToggleGroup();
        rdCp2.setToggleGroup(selectInicio);
        rdCp1.setToggleGroup(selectInicio);
        rdCp2.setFont(tipoLetter);
        rdCp1.setFont(tipoLetter);
        hbradioButton.getChildren().addAll(rdCp1,rdCp2);
        hbradioButton.setSpacing(20);
        ToggleGroup selectOpcion = new ToggleGroup();
        rdCR.setToggleGroup(selectOpcion);
        rdCX.setToggleGroup(selectOpcion);
        rdCR.setFont(tipoLetter);
        rdCX.setFont(tipoLetter);
        hbOpciones.getChildren().addAll(rdCR,rdCX);
        hbOpciones.setSpacing(45);
    }   

    public void seleccionarFichasJugadores() {
        rdCR.setOnMouseClicked(event -> {      
            if (rdCR.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU2.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
        rdCX.setOnMouseClicked(event -> {
            if (rdCX.isSelected()) {
                try (FileInputStream input = new FileInputStream("src/main/resources/Images/letra-x.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU1.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }

                try (FileInputStream input = new FileInputStream("src/main/resources/Images/circulo.png")) {

                    Image image = new Image(input, 90, 100, true, false);
                    imgCPU2.setImage(image);

                } catch (IOException exep) {
                    System.out.println("error");
                }
            }

        });
    }        
    
}
