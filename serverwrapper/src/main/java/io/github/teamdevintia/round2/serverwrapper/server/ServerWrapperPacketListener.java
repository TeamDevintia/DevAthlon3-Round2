package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import lombok.extern.java.Log;

/**
 * Created by Martin on 24.07.2016.
 */
@Log
public class ServerWrapperPacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof ComponentPacket) {
            ComponentPacket compPack = (ComponentPacket) event.getReceivedPacket();
            log.info("Got packet from " + event.getEnumPacketDirection());
            log.info("message:" + compPack.getMessage());
            //TODO event listener
        }
    }
}
