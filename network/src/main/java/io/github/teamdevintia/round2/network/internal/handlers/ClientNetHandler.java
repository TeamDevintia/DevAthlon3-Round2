package io.github.teamdevintia.round2.network.internal.handlers;

import io.github.teamdevintia.round2.network.Callback;
import io.github.teamdevintia.round2.network.NetHandler;
import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.Packet;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;

/**
 * @author Shad0wCore
 */
@Getter
public class ClientNetHandler extends NetHandler {

    private StreamHandler streamHandler;
    private EventBus eventBus;

    public ClientNetHandler(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public void addToSendQueue(Packet packet) {
        this.streamHandler.handlePacket(packet);
    }

    @Override
    public void establishConnection(String host, int port, Callback<StreamHandler> handlerCallback) {
        EventLoopGroup rootGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(rootGroup)
                    .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            handlerCallback.trigger(preparePipeline(channel));
                        }
                    }).connect(host, port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
