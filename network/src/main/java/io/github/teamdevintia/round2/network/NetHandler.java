package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.StreamHandler;
import io.github.teamdevintia.round2.network.packet.Packet;
import io.netty.channel.Channel;

/**
 * @author Shad0wCore
 */
public interface NetHandler {

    void addToSendQueue(Packet packet);

    void establishConnection(String host, int port,  Callback<StreamHandler> handlerCallback);

    StreamHandler preparePipeline(Channel channel);

}
