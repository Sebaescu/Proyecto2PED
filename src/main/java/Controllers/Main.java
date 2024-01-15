package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        
        MenuPrincipalController menuController = loader.getController();
        menuController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Tic-Tac-Tou");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("file:Logo/Imagen2.jpg"));        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

