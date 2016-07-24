package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.packet.*;

/**
 * @author Shad0wCore
 */
public enum Protocol {

    COMPONENT_PACK(1111111, ComponentPacket.class),

    CREATE_SERVER_PACKET(1, CreateServerPacket.class),
    CREATED_SERVER_PACKET(11, CreatedServerPacket.class),

    DELETE_SERVER_PACKET(2, DeleteServerPacket.class),
    DELETED_SERVER_PACKET(22, DeletedServerPacket.class),

    START_SERVER_PACKET(3, StartServerPacket.class),
    STARTED_SERVER_PACKET(33, StartedServerPacket.class),

    STOP_SERVER_PACKET(4, StopServerPacket.class),
    STOPPED_SERVER_PACKET(44, StoppedServerPacket.class),

    KEEP_ALIVE_PACKET(5, KeepAlivePacket.class),
    KEEP_ALIVE_CONFIRMED_PACKET(55, KeepAliveConfirmedPacket.class),

    SERVER_INFO_PACKET(6, ServerInfoPacket.class),

    CONSOLE_COMMAND_PACKET(7, ConsoleCommandPacket.class);


    private int packetID;
    private Class<? extends Packet> packetClass;

    Protocol(int packetID, Class<? extends Packet> packetClass) {
        this.packetID = packetID;
        this.packetClass = packetClass;
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

    public Class<? extends Packet> getPacketClass() {
        return this.packetClass;
    }

    public int getPacketID() {
        return this.packetID;
    }

}
