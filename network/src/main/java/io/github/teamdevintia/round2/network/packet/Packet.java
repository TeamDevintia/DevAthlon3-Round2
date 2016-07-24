package io.github.teamdevintia.round2.network.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
 */
public abstract class Packet<T> {

    public abstract void write(ByteBuf byteBuf);

    public abstract void read(ByteBuf byteBuf);

    public abstract EnumPacketDirection getEnumPacketDirection();

}


