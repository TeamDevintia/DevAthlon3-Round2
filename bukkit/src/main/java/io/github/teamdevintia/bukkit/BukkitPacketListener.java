package io.github.teamdevintia.bukkit;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;

/**
 * The packet listener for this plugin
 *
 * @author MiniDigger
 */
public class BukkitPacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof ComponentPacket) {
            ComponentPacket compPack = (ComponentPacket) event.getReceivedPacket();
            System.out.println("Got packet from " + event.getEnumPacketDirection());
            System.out.println("message:" + compPack.getMessage());
        }
    }
}