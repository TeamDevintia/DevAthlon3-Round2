package io.github.teamdevintia.round2.client.constants.scenes;

import io.github.teamdevintia.round2.client.util.FXMLUtil;
import javafx.scene.Parent;
import javafx.scene.control.TreeView;

import java.io.IOException;

/**
 * @author Shad0wCore
 */
public class MainSceneConstants {

    public static Parent MAIN_FRAMEWORK;
    public static TreeView SERVER_PICKER;

    static {
        try {
            MAIN_FRAMEWORK = FXMLUtil.getParent("/documents/fxml/main_framework.fxml");
            SERVER_PICKER = FXMLUtil.getNode("serverControls", MAIN_FRAMEWORK);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
