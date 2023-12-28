/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abeni
 */
public class MenuPrincipalController implements Initializable {
    
    public Stage primaryStage;
    @FXML
    private AnchorPane paneMenu;
    @FXML
    private Button btCPU;
    @FXML
    private Button btjugador;
    @FXML
    private Button btcerrar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void abrirVScomputador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VsComputador.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del VsComputador.fxml
            VsComputadorController vsComputadorController = loader.getController();
            setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void abrirVSjugador(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VsJugador.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del VsComputador.fxml
            //VsComputadorController vsComputadorController = loader.getController();
            setPrimaryStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salirJuego(ActionEvent event) {
        Stage stage = (Stage) btcerrar.getScene().getWindow();
        stage.close();
    }

}
