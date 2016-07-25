package io.github.teamdevintia.round2.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author Shad0wCore
 */
public class FXMLUtil {

    public static Parent getParent(String documentPath) throws IOException {
        return FXMLLoader.load(FXMLUtil.class.getResource(documentPath));
    }

    public static <T extends Node> T getNode(String id, Parent from) {
        return (T) from.lookup("#" + id);
    }

}
