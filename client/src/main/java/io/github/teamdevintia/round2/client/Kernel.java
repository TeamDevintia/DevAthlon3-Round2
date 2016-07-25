package io.github.teamdevintia.round2.client;

import io.github.teamdevintia.round2.client.gui.Bounding;
import javafx.stage.Stage;

/**
 * @author Shad0wCore
 */
public interface Kernel {

    Bounding getBounding();

    Stage getPrimaryStage();

}
