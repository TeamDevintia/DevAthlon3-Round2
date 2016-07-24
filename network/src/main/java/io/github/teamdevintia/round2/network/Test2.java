package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.packet.CreateServerPacket;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;

/**
 * @author Shad0wCore
 */
public class Test2 {

    private static ClientNetHandler clientNetHandler;

    public static void main(String[] args) {
        EventBus secondEventBus = new EventBus();
        secondEventBus.registerEvent(new PacketEventHandler(new TestEvent2(), () -> {
        }, "kacken2"));

        clientNetHandler = new ClientNetHandler(secondEventBus);
        clientNetHandler.establishConnection("127.0.0.1", 8000, new Callback<StreamHandler>() {
            @Override
            public void trigger(StreamHandler streamHandler) {
                streamHandler.handlePacket(new CreateServerPacket("testServer-1", "SPIGOT", "v1_8_3R1", 10023));
            }
        });
    }

}
