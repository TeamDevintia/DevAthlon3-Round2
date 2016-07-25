package io.github.teamdevintia.round2.client.gui;


import io.github.teamdevintia.round2.client.Kernel;
import io.github.teamdevintia.round2.client.PrimaryInitializer;
import io.github.teamdevintia.round2.client.constants.scenes.MainSceneConstants;
import io.github.teamdevintia.round2.client.gui.scenes.BaseScene;
import io.github.teamdevintia.round2.client.gui.scenes.MainScene;

/**
 * @author Shad0wCore
 */
public class CentralGuiUnit implements PrimaryInitializer {

    private static Kernel kernel;

    public void initialize(Kernel kernel) {
        CentralGuiUnit.kernel = kernel;
        initializeScene(new MainScene(MainSceneConstants.MAIN_FRAMEWORK, kernel.getBounding()));
    }

    public static void initializeScene(BaseScene baseScene) {
        baseScene.initializeScene(kernel);
    }

}
