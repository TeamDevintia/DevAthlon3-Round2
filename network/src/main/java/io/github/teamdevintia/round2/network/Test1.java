package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.WrapperServerNetHandler;

/**
 * @author Shad0wCore
 */
public class Test1 {

    private static WrapperServerNetHandler wrapperServerNetHandler;

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.registerEvent(new PacketEventHandler(new TestEvent(), () -> {
        }, "kackhaufen"));

        wrapperServerNetHandler = new WrapperServerNetHandler(eventBus);
        wrapperServerNetHandler.establishServerConnection("127.0.0.1", 8000, streamHandler -> {
        });

    }

    public static WrapperServerNetHandler getWrapperServerNetHandler() {
        return wrapperServerNetHandler;
    }
}
