package io.github.teamdevintia.round2.network.pipeline;

import io.github.teamdevintia.round2.network.Protocol;
import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shad0wCore
 */
public class PipelineEncoder extends MessageToByteEncoder<Packet> {

    private EventBus eventBus;

    public PipelineEncoder(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {

        int packetID = Protocol.getIDFromPacket(packet.getClass());

        if (packetID < 1) {
            Logger.getGlobal().log(Level.WARNING, "Caught unexpected packetID (" + packetID + ")");
            byteBuf.release();
            return;
        }

        byteBuf.writeInt(packetID);
        packet.write(byteBuf);
    }

}
