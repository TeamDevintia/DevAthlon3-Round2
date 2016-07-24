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
public class DeleteServerPacket extends Packet {

    private String name;

    public DeleteServerPacket() {
    }

    @Override
    public void write(ByteBuf byteBuf) {

    }

    @Override
    public void read(ByteBuf byteBuf) {

    }

}
