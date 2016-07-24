package io.github.teamdevintia.round2.network.internal.events;

import io.github.teamdevintia.round2.network.internal.Event;
import io.github.teamdevintia.round2.network.EnumPacketDirection;
import io.github.teamdevintia.round2.network.Packet;

/**
 * @author Shad0wCore
 */
public class PacketReceiveEvent extends Event {

    private Packet receivedPacket;
    private EnumPacketDirection enumPacketDirection;

    public PacketReceiveEvent(Packet receivedPacket) {
        this.receivedPacket = receivedPacket;
        //TODO do we need this?
       // this.enumPacketDirection = this.receivedPacket.getEnumPacketDirection();
    }

    public EnumPacketDirection getEnumPacketDirection() {
        return enumPacketDirection;
    }

    public Packet getReceivedPacket() {
        return receivedPacket;
    }
}
