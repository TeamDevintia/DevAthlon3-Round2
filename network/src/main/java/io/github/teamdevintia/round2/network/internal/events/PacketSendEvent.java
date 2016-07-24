package io.github.teamdevintia.round2.network.internal.events;

import io.github.teamdevintia.round2.network.internal.Event;
import io.github.teamdevintia.round2.network.EnumPacketDirection;
import io.github.teamdevintia.round2.network.Packet;

/**
 * @author Shad0wCore
 */
public class PacketSendEvent extends Event {

    private Packet sentPacket;
    private EnumPacketDirection enumPacketDirection;
    public PacketSendEvent(Packet sentPacket) {
        this.sentPacket = sentPacket;
        this.enumPacketDirection = this.sentPacket.getEnumPacketDirection();
    }

    public Packet getSentPacket() {
        return sentPacket;
    }

    public void setSentPacket(Packet sentPacket) {
        this.sentPacket = sentPacket;
    }

    public EnumPacketDirection getEnumPacketDirection() {
        return this.enumPacketDirection;
    }

    public void setEnumPacketDirection(EnumPacketDirection enumPacketDirection) {
        this.enumPacketDirection = enumPacketDirection;
    }
}
