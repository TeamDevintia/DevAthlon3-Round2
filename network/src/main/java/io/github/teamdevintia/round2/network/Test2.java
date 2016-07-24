package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;

/**
 * @author Shad0wCore
 */
public class Test2 {

    public static void main(String[] args) {
        EventBus secondEventBus = new EventBus();
        ClientNetHandler clientNetHandler = new ClientNetHandler(secondEventBus);
        clientNetHandler.establishConnection("127.0.0.1", 8000, new Callback<StreamHandler>() {
            @Override
            public void trigger(StreamHandler streamHandler) {

            }
        });
    }

}
