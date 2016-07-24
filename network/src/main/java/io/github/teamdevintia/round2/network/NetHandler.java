package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.github.teamdevintia.round2.network.pipeline.PipelineDecoder;
import io.github.teamdevintia.round2.network.pipeline.PipelineEncoder;
import io.netty.channel.Channel;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Shad0wCore
 */
public abstract class NetHandler {

    protected EventBus eventBus;
    protected StreamHandler streamHandler;

    public NetHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void addToSendQueue(Packet packet) {
        this.streamHandler.handlePacket(packet);
    }

    public abstract void establishConnection(String host, int port,  Callback<StreamHandler> handlerCallback);

    public final StreamHandler preparePipeline(Channel channel) {
        this.streamHandler = new StreamHandler(this.eventBus);
        channel.pipeline().addLast(new LengthFieldPrepender(4, true))
                .addLast(new PipelineDecoder(this.eventBus)).addLast(new PipelineEncoder(this.eventBus))
                .addLast(this.streamHandler);
        return this.streamHandler;
    }

}
