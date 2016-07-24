package io.github.teamdevintia.round2.network.customs;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;

/**
 * @author Shad0wCore
 */
public class ReceiveEvent implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {

        System.out.println(event.getEnumPacketDirection());
        System.out.println(event.getReceivedPacket());

    }

}
