package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.handlers.WrapperServerNetHandler;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Shad0wCore
 */
public class Test1 {

    private static Set<Channel> caughtChannels = new HashSet<>();
    private static Set<StreamHandler> caughtStreamHandlers = new HashSet<>();

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        WrapperServerNetHandler wrapperServerNetHandler = new WrapperServerNetHandler(eventBus);
        wrapperServerNetHandler.establishServerConnection("127.0.0.1", 8000, new Callback<StreamHandler>() {
            @Override
            public void trigger(StreamHandler streamHandler) {
                caughtChannels.add(streamHandler.getChannel());
                caughtStreamHandlers.add(streamHandler);
            }
        });

    }

}
