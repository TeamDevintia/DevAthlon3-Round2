package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import lombok.extern.java.Log;

/**
 * Adds a newly created server to the proxy or deletes removed ones
 *
 * @author MiniDigger
 */
@Log
public class BungeePacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof ComponentPacket) {
            ComponentPacket compPack = (ComponentPacket) event.getReceivedPacket();
            log.info("Got packet from " + event.getEnumPacketDirection());
            log.info("message:" + compPack.getMessage());
            //TODO if we get a CreatedServerPacket, get the port and add that server to the proxy

//            API.addServer(name, "localhost",port);
        }
    }
}