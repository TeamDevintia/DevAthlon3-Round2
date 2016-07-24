package io.github.teamdevintia.round2.network.packet;

import io.github.teamdevintia.round2.network.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shad0wCore
 */
@Getter
@AllArgsConstructor
public class ServerInfoPacket extends Packet {

    private String name;
    private int numPlayer;
    private int maxPlayer;
    private long freeRam;
    private long maxRam;
    private double[] tps;

    public ServerInfoPacket() {
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    @Override
    public void read(ByteBuf byteBuf) {

    }

}
