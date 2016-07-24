package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.StreamHandler;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.github.teamdevintia.round2.network.pipeline.PipelineDecoder;
import io.github.teamdevintia.round2.network.pipeline.PipelineEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Shad0wCore
 */
public class Test1 implements NetHandler {

    public static void main(String[] args) {
        new Test1().establishConnection("localhost", 8000, null);
    }

    @Override
    public void establishConnection(String host, int port, Callback<StreamHandler> handlerCallback) {
        EventLoopGroup rootGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        EventLoopGroup childGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        try {
            serverBootstrap.group(rootGroup, childGroup)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            preparePipeline(channel);
                        }
                    }).bind(host, port).sync().channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToSendQueue(Packet<?> packet) {

    }

    @Override
    public StreamHandler preparePipeline(Channel channel) {
        StreamHandler streamHandler = new StreamHandler();
        channel.pipeline().addLast(new LengthFieldPrepender(4, true))
                .addLast(new PipelineDecoder()).addLast(new PipelineEncoder())
                .addLast(streamHandler);
        return streamHandler;
    }
}
