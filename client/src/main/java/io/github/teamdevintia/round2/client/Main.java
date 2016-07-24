package io.github.teamdevintia.round2.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Martin on 24.07.2016.
 */
public class Main extends Application {

    private static Main instance;

    public void start(Stage primaryStage) throws Exception {
        instance = this;

        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();

    }

    public static Main getInstance() {
        return instance;
    }
}
