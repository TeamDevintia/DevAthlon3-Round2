package io.github.teamdevintia.round2.client.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Shad0wCore
 */
public class ImageUtil {

    public static Image getImage(String path) {
        return new Image(ImageUtil.class.getResource(path).toExternalForm());
    }

    public static ImageView getPreparedImage(Image image, boolean fitIn) {
        ImageView tempImageView = new ImageView(image);

        if (fitIn) {
            tempImageView.fitWidthProperty().bind(image.widthProperty());
            tempImageView.fitHeightProperty().bind(image.heightProperty());
        }

        return tempImageView;
    }

    public static ImageView getPreparedImage(String path, boolean fitIn) {
        Image tempImage = getImage(path);
        ImageView tempImageView = new ImageView(tempImage);

        if (fitIn) {
            tempImageView.fitWidthProperty().bind(tempImage.widthProperty());
            tempImageView.fitHeightProperty().bind(tempImage.heightProperty());
        }

        return tempImageView;
    }

}
