package io.github.teamdevintia.round2.network.internal.handlers;

import io.github.teamdevintia.round2.network.Callback;
import io.github.teamdevintia.round2.network.ServerNetHandler;
import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.events.ChannelInitializedEvent;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Shad0wCore
 */
public class WrapperServerNetHandler extends ServerNetHandler {

    public WrapperServerNetHandler(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public void addToSendQueue(Packet packet) {
        this.streamHandler.handlePacket(packet);
    }

    @Override
    public void establishServerConnection(String host, int port, Callback<StreamHandler> handlerCallback) {
        EventLoopGroup rootGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        EventLoopGroup childGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();


        serverBootstrap.group(rootGroup, childGroup)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelInitializedEvent channelInitializedEvent = new ChannelInitializedEvent(channel);
                        eventBus.callEvent(channelInitializedEvent);
                        if (channelInitializedEvent.isCancelled()) {
                            channel.disconnect();
                            return;
                        }

                        handlerCallback.trigger(preparePipeline(channel));
                    }
                }).bind(host, port);

    }

}
