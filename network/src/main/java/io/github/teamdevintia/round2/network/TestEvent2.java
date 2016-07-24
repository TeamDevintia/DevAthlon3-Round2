package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.CreatedServerPacket;

/**
 * @author Shad0wCore
 */
public class TestEvent2 implements PipelineEventListener {

    @PipelineEvent
    public void onReceive2(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof CreatedServerPacket) {
            CreatedServerPacket createdServerPacket = (CreatedServerPacket) event.getReceivedPacket();
            System.out.println(createdServerPacket.getName());
            System.out.println(createdServerPacket.getMod());
            System.out.println(createdServerPacket.getVersion());
            System.out.println(createdServerPacket.getPort());
        }

    }

}
