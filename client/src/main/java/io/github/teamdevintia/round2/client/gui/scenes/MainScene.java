package io.github.teamdevintia.round2.client.gui.scenes;

import io.github.teamdevintia.round2.client.Kernel;
import io.github.teamdevintia.round2.client.gui.Bounding;
import javafx.beans.NamedArg;
import javafx.scene.Parent;

/**
 * @author Shad0wCore
 */
public class MainScene extends BaseScene {

    public MainScene(@NamedArg("root") Parent root, Bounding bounding) {
        super(root, bounding);
    }

    public void initializeScene(Kernel kernel) {
        kernel.getPrimaryStage().setScene(this);
    }

}
