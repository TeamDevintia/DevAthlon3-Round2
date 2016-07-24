package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.handlers.WrapperServerNetHandler;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;

/**
 * @author Shad0wCore
 */
public class Test1 {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        WrapperServerNetHandler wrapperServerNetHandler = new WrapperServerNetHandler(eventBus);
        wrapperServerNetHandler.establishServerConnection("127.0.0.1", 8000, new Callback<StreamHandler>() {
            @Override
            public void trigger(StreamHandler streamHandler) {
            }
        });

    }

}
