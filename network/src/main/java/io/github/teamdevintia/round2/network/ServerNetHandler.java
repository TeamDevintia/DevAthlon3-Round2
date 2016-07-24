package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.github.teamdevintia.round2.network.pipeline.PipelineDecoder;
import io.github.teamdevintia.round2.network.pipeline.PipelineEncoder;
import io.netty.channel.Channel;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shad0wCore
 */
public abstract class ServerNetHandler {

    protected EventBus eventBus;
    protected List<StreamHandler> streamHandlers = new ArrayList<>();

    public ServerNetHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void addToSendQueue(Packet packet) {
        this.streamHandlers.forEach(streamHandler -> streamHandler.handlePacket(packet));
    }

    public abstract void establishServerConnection(String host, int port,  Callback<StreamHandler> handlerCallback);

    public final StreamHandler preparePipeline(Channel channel) {
        StreamHandler streamHandler = new StreamHandler(this.eventBus);
        channel.pipeline().addLast(new LengthFieldPrepender(4, true))
                .addLast(new PipelineDecoder(this.eventBus)).addLast(new PipelineEncoder(this.eventBus))
                .addLast(streamHandler);
        return streamHandler;
    }

}
