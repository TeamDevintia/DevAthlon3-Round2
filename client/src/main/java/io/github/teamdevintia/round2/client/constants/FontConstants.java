package io.github.teamdevintia.round2.client.constants;


import javafx.scene.text.Font;

/**
 * @author Shad0wCore
 */
public class FontConstants {

    public static Font BANKSIA_REGULAR_13PX;
    public static Font BANKSIA_REGULAR_15PX;
    public static Font BANKSIA_REGULAR_17PX;
    public static Font BANKSIA_REGULAR_19PX;
    public static Font BANKSIA_SEMI_BOLD13PX;
    public static Font BANKSIA_SEMI_BOLD15PX;
    public static Font BANKSIA_SEMI_BOLD17PX;
    public static Font BANKSIA_SEMI_BOLD19PX;
    public static Font BANKSIA_BOLD13PX;
    public static Font BANKSIA_BOLD15PX;
    public static Font BANKSIA_BOLD17PX;
    public static Font BANKSIA_BOLD19PX;

    static {
        BANKSIA_REGULAR_13PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_regular.ttf").toExternalForm(), 13);
        BANKSIA_REGULAR_15PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_regular.ttf").toExternalForm(), 15);
        BANKSIA_REGULAR_17PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_regular.ttf").toExternalForm(), 17);
        BANKSIA_REGULAR_19PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_regular.ttf").toExternalForm(), 19);

        BANKSIA_SEMI_BOLD13PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_semi_bold.ttf").toExternalForm(), 13);
        BANKSIA_SEMI_BOLD15PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_semi_bold.ttf").toExternalForm(), 15);
        BANKSIA_SEMI_BOLD17PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_semi_bold.ttf").toExternalForm(), 17);
        BANKSIA_SEMI_BOLD19PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_semi_bold.ttf").toExternalForm(), 19);

        BANKSIA_BOLD13PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_bold.ttf").toExternalForm(), 13);
        BANKSIA_BOLD15PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_bold.ttf").toExternalForm(), 15);
        BANKSIA_BOLD17PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_bold.ttf").toExternalForm(), 17);
        BANKSIA_BOLD19PX = Font.loadFont(FontConstants.class.getResource("/documents/fonts/banksia_bold.ttf").toExternalForm(), 19);
    }

}
