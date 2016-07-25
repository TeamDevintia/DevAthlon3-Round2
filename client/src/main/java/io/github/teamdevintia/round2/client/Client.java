package io.github.teamdevintia.round2.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/**
 *
 * @author batthomas and Shad0wCore
 *
 */
public class Client extends Application{

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ui/Client.fxml"));
            
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle("Client");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(BungeeCordBridge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
