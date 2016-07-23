package io.github.teamdevintia.round2.network.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author Shad0wCore
 */
public abstract class Packet<T> {

    /**
     * The id of the packet
     */
    public abstract int getPacketID();

    /**
     * The direction where the packet will be sent to
     */
    public abstract EnumPacketDirection getPacketDirection();

    public abstract void write(ByteBuf byteBuf);

    public abstract void read(ByteBuf byteBuf);

}


