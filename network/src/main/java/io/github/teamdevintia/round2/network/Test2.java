package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.packet.EnumPacketDirection;
import io.github.teamdevintia.round2.network.pipeline.StreamHandler;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import io.github.teamdevintia.round2.network.pipeline.MessageSerializer;

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
                MessageSerializer messageSerializer = new MessageSerializer("information");
                messageSerializer.addProperty("numPlayers", 123). addProperty("maxPlayers", 234);
                messageSerializer.addProperty("currentRam", 1024). addProperty("maxRam", 3056);
                messageSerializer.addProperty("tps", 20). addProperty("motd", "A Minecraft Server");
                streamHandler.handlePacket(new ComponentPacket(EnumPacketDirection.GLOBAL, messageSerializer.serialize()));
            }
        });
    }

}
