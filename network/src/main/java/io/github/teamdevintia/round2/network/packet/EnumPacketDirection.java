package io.github.teamdevintia.round2.network.packet;

/**
 * @author Shad0wCore
 */
public enum  EnumPacketDirection {

    WRAPPER,
    PROXY,
    CLIENT;


    public static EnumPacketDirection[] directions(EnumPacketDirection... enumPacketDirections) {
        return enumPacketDirections;
    }
}
