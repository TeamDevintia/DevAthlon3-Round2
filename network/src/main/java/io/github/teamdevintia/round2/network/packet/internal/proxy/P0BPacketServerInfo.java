package io.github.teamdevintia.round2.network.packet.internal.proxy;

import io.github.teamdevintia.round2.network.packet.EnumPacketDirection;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
 *         Represents a packet which will send the proxy to the client
 *         to return the required informations
 *
 *         For more see the JavaDocs at {@link Packet}
 */
public class P0BPacketServerInfo extends Packet {

    public int getPacketID() {
        return 0;
    }

    public EnumPacketDirection getPacketDirection() {
        return null;
    }

    public void write(ByteBuf byteBuf) {

    }

    public void read(ByteBuf byteBuf) {

    }

}
