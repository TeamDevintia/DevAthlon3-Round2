package io.github.teamdevintia.round2.network.internal;

import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Shad0wCore
 */
public class StreamHandler extends SimpleChannelInboundHandler<Packet> {

    private Channel channel;
    private BlockingQueue<Packet> sendBeforeConnected = new LinkedBlockingDeque<>();

    public void handlePacket(Packet packet) {
        if (this.channel == null) {
            this.sendBeforeConnected.add(packet);
            return;
        }

        channel.writeAndFlush(packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        this.sendBeforeConnected.forEach(packet -> channel.writeAndFlush(packet));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        if (packet instanceof ComponentPacket) {
            ComponentPacket componentPacket = (ComponentPacket) packet;
            System.out.println(componentPacket.getMessage());
        }
    }


}
