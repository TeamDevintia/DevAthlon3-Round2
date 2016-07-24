package io.github.teamdevintia.bukkit;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;

/**
 * The packet listener for this plugin
 *
 * @author MiniDigger
 */
public class BukkitPacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        // TODO bukkit packet listener
    }
}