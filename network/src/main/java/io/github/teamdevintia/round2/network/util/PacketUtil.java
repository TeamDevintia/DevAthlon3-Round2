package io.github.teamdevintia.round2.network.util;

import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
 */
public class PacketUtil {

    public static int readPacketID(ByteBuf byteBuf) {
        int packetID = byteBuf.readInt();
        return packetID;
    }

}
