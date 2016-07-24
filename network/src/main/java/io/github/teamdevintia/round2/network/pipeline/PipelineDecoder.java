package io.github.teamdevintia.round2.network.pipeline;

import io.github.teamdevintia.round2.network.Protocol;
import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.Packet;
import io.github.teamdevintia.round2.network.util.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Shad0wCore
 */
public class PipelineDecoder extends ByteToMessageDecoder {

    private EventBus eventBus;

    public PipelineDecoder(EventBus eventBus) {
        this.eventBus = eventBus;
    }


    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Class<? extends Packet> translatedPacketClass = Protocol.packetViaID(PacketUtil.readPacketID(byteBuf));
        if (translatedPacketClass != null) {
            Packet transformedPacket = translatedPacketClass.newInstance();
            transformedPacket.read(byteBuf);
            list.add(transformedPacket);
        }

    }

}
