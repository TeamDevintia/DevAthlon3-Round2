package io.github.teamdevintia.round2.network.packet.internal.proxy;

import io.github.teamdevintia.round2.network.packet.EnumPacketDirection;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
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
