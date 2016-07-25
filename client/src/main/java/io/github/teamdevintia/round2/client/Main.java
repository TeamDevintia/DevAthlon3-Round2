package io.github.teamdevintia.round2.client;

import io.github.teamdevintia.round2.client.gui.Bounding;
import io.github.teamdevintia.round2.client.gui.CentralGuiUnit;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application implements Kernel {

    private static Stage primaryStage;
    private static Bounding primaryBounding = new Bounding(750, 250, 1000, 500);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage rootStage) throws Exception {
        primaryStage = rootStage;

        CentralGuiUnit centralGuiUnit = new CentralGuiUnit();
        centralGuiUnit.initialize(this);

        rootStage.setTitle("Dynamic Server Interface");
        rootStage.show();

    }

    public Bounding getBounding() {
        return primaryBounding;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

}
