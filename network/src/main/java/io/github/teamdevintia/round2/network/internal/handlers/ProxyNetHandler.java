package io.github.teamdevintia.round2.network.internal.handlers;

import io.github.teamdevintia.round2.network.Callback;
import io.github.teamdevintia.round2.network.NetHandler;
import io.github.teamdevintia.round2.network.internal.StreamHandler;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.github.teamdevintia.round2.network.pipeline.PipelineDecoder;
import io.github.teamdevintia.round2.network.pipeline.PipelineEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Shad0wCore
 */
public class ProxyNetHandler implements NetHandler {

    private StreamHandler streamHandler;

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

    @Override
    public StreamHandler preparePipeline(Channel channel) {
        this.streamHandler = new StreamHandler();
        channel.pipeline().addLast(new LengthFieldPrepender(4, true))
                .addLast(new PipelineDecoder()).addLast(new PipelineEncoder())
                .addLast(this.streamHandler);
        return this.streamHandler;
    }

}
