package io.github.teamdevintia.round2.network;

/**
 * @author Shad0wCore
 */
public enum  EnumPacketDirection {

    GLOBAL,
    WRAPPER,
    PROXY,
    CLIENT;


    public static EnumPacketDirection[] directions(EnumPacketDirection... enumPacketDirections) {
        return enumPacketDirections;
    }
}
