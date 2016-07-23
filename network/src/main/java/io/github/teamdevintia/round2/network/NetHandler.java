package io.github.teamdevintia.round2.network;

import io.github.teamdevintia.round2.network.packet.Packet;

/**
 * @author Shad0wCore
 */
public interface NetHandler {

    void processInitialization(Packet<?>... toRegisterPackets);

    void establishConnection(String host, int port);

    void addToSendQueue(Packet<?> packet);

}
