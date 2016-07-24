package io.github.teamdevintia.round2.network.packet;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * @author Shad0wCore
 */
public class ComponentPacket extends Packet {

    private String message;
    private EnumPacketDirection enumPacketDirection;

    public ComponentPacket() {
    }

    public ComponentPacket(EnumPacketDirection enumPacketDirection, String jsonMessage) {
        this.message = jsonMessage;
        this.enumPacketDirection = enumPacketDirection;
    }

    public void write(ByteBuf byteBuf) {
        byte[] messageBytes = this.message.getBytes(Charset.forName("UTF-8"));
        byteBuf.writeInt(messageBytes.length);
        byteBuf.writeBytes(messageBytes);
    }

    public void read(ByteBuf byteBuf) {
        int byteLength = byteBuf.readInt();
        byte[] messageBytes = new byte[byteLength];
        byteBuf.readBytes(messageBytes);
        this.message = new String(messageBytes, Charset.forName("UTF-8"));
    }

    @Override
    public EnumPacketDirection getEnumPacketDirection() {
        return this.enumPacketDirection;
    }

    public String getMessage() {
        return this.message;
    }

}
