package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.CreateServerPacket;
import io.github.teamdevintia.round2.network.packet.CreatedServerPacket;

/**
 * @author Shad0wCore
 */
public class TestEvent implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof CreateServerPacket) {
            CreateServerPacket createServerPacket = (CreateServerPacket) event.getReceivedPacket();
            System.out.println(createServerPacket.getName());
            System.out.println(createServerPacket.getMod());
            System.out.println(createServerPacket.getVersion());
            System.out.println(createServerPacket.getPort());

            Test1.getWrapperServerNetHandler().addToSendQueue(new CreatedServerPacket(createServerPacket.getName(),
                    createServerPacket.getMod(), createServerPacket.getVersion(), createServerPacket.getPort()));

        }
    }

}
