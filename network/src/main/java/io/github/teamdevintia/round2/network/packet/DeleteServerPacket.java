package io.github.teamdevintia.round2.network.packet;

import io.github.teamdevintia.round2.network.EnumPacketDirection;
import io.github.teamdevintia.round2.network.Packet;
import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
 */
public class DeleteServerPacket extends Packet {

    @Override
    public void write(ByteBuf byteBuf) {

    }

    @Override
    public void read(ByteBuf byteBuf) {

    }

    @Override
    public EnumPacketDirection getEnumPacketDirection() {
        return null;
    }
}
