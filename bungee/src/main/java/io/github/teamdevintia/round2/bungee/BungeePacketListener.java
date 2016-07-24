package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.CreatedServerPacket;

/**
 * Adds a newly created server to the proxy or deletes removed ones
 *
 * @author MiniDigger
 */
public class BungeePacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof CreatedServerPacket) {
            CreatedServerPacket packet = (CreatedServerPacket) event.getReceivedPacket();
            API.addServer(packet.getName(), "localhost", packet.getPort());
            Main.getInstance().getLogger().info("Added server " + packet.getName() + " with port " + packet.getPort());
        }
    }
}