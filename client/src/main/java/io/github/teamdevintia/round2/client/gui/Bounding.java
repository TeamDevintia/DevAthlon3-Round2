package io.github.teamdevintia.round2.client.gui;

/**
 * @author Shad0wCore
 */
public class Bounding {

    private double minWidth, minHeight;
    private double width, height;

    public Bounding() {
        this(0, 0, 0, 0);
    }

    public Bounding(double width, double height) {
        this(0, 0, width, height);
    }

    public Bounding(double minWidth, double minHeight, double width, double height) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.width = width;
        this.height = height;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(double minWidth) {
        this.minWidth = minWidth;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
