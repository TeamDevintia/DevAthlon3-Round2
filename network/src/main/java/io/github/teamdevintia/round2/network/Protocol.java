package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.packet.ComponentPacket;

/**
 * @author Shad0wCore
 */
public enum Protocol {

    COMPONENT_PACK(1, ComponentPacket.class);

    private int packetID;
    private Class<? extends Packet> packetClass;

    Protocol(int packetID, Class<? extends Packet> packetClass) {
        this.packetID = packetID;
        this.packetClass = packetClass;
    }

    public Class<? extends Packet> getPacketClass() {
        return this.packetClass;
    }

    public int getPacketID() {
        return this.packetID;
    }

    public static Class<? extends Packet> packetViaID(int packetID) {
        for (Protocol protocol : values()) {
            if (packetID == protocol.getPacketID()) {
                return protocol.getPacketClass();
            }
        }
        return null;
    }

    public static int getIDFromPacket(Class<? extends Packet> packetClass) {
        for (Protocol protocol : values()) {
            if (protocol.getPacketClass().isAssignableFrom(packetClass)) {
                return protocol.getPacketID();
            }
        }
        return -1;
    }

}
