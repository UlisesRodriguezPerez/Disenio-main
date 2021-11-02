/**
 * Trabajo realizado por: 
 * Fabrizio Ferreto Saborío 2019177147
 * Ulises Rodríguez Perez 2019380067
 * Fernando Álvarez 2019171657
 * Crytel Montero Obando 2019158736
 * */

package Vista;

import Modelo.FactoryPattern.PrototypeFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXMLS/principal.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        PrototypeFactory.initiliazeFactory();
        launch(args);
    }
}
