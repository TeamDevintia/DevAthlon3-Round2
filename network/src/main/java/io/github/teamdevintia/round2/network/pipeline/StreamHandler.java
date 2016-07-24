package io.github.teamdevintia.round2.network.pipeline;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.internal.events.PacketSendEvent;
import io.github.teamdevintia.round2.network.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Shad0wCore
 */
public class StreamHandler extends ChannelDuplexHandler {

    private Channel channel;
    private BlockingQueue<Packet> unhandledPackets = new LinkedBlockingDeque<>();
    private EventBus eventBus;

    public StreamHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void handlePacket(Packet packet) {
        if (this.channel == null) {
            this.unhandledPackets.add(packet);
            return;
        }

        channel.writeAndFlush(packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.channel = ctx.channel();
        this.unhandledPackets.forEach(packet -> channel.writeAndFlush(packet));
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        if (packet instanceof Packet) {
            PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent((Packet) packet);
            this.eventBus.callEvent(packetReceiveEvent);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
        if (packet instanceof Packet) {
            PacketSendEvent packetSendEvent = new PacketSendEvent((Packet) packet);
            this.eventBus.callEvent(packetSendEvent);
        }

        super.write(ctx, packet, promise);
    }

    public Channel getChannel() {
        return channel;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
