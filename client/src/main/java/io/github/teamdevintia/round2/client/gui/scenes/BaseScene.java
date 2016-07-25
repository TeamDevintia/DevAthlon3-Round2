package io.github.teamdevintia.round2.client.gui.scenes;

import io.github.teamdevintia.round2.client.Kernel;
import io.github.teamdevintia.round2.client.constants.StyleConstants;
import io.github.teamdevintia.round2.client.gui.Bounding;
import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author Shad0wCore
 */
public abstract class BaseScene extends Scene {

    private double sceneCenterX, sceneCenterY;
    private String frameworkStylePath = StyleConstants.STYLE_FRAMEWORK;
    private Bounding bounding;

    public BaseScene(@NamedArg("root") Parent root, Bounding bounding) {
        super(root, bounding.getWidth(), bounding.getHeight());
        super.getStylesheets().add(this.frameworkStylePath);

        this.bounding = bounding;
        this.sceneCenterX = this.bounding.getWidth() / 2;
        this.sceneCenterY = this.bounding.getHeight() / 2;
    }

    public abstract void initializeScene(Kernel kernel);

    protected double getSceneCenterX() {
        return this.sceneCenterX;
    }

    protected double getSceneCenterY() {
        return this.sceneCenterY;
    }

    protected Bounding getBoundings() {
        return this.bounding;
    }

}
