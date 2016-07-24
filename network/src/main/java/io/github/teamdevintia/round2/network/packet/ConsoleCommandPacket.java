package io.github.teamdevintia.round2.network.packet;

import io.github.teamdevintia.round2.network.Packet;
import io.github.teamdevintia.round2.network.pipeline.MessageDeserializer;
import io.github.teamdevintia.round2.network.pipeline.MessageSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.nio.charset.Charset;

/**
 * @author MiniDigger
 */
@Getter
@AllArgsConstructor
public class ConsoleCommandPacket extends Packet {

    private String serverName;
    private String command;

    @Override
    public void write(ByteBuf byteBuf) {
        MessageSerializer messageSerializer = new MessageSerializer("consoleCommand");
        messageSerializer.addProperty("serverName", serverName).addProperty("command", command);

        byte[] messageBytes = messageSerializer.serialize().getBytes(Charset.forName("UTF-8"));
        byteBuf.writeInt(messageBytes.length);
        byteBuf.writeBytes(messageBytes);
    }

    @Override
    public void read(ByteBuf byteBuf) {
        int byteLength = byteBuf.readInt();
        byte[] messageBytes = new byte[byteLength];
        byteBuf.readBytes(messageBytes);

        try {
            MessageDeserializer messageDeserializer = new MessageDeserializer("consoleCommand");
            JSONObject properties = messageDeserializer.deserialize(new String(messageBytes, Charset.forName("UTF-8")));

            this.serverName = (String) properties.get("serverName");
            this.command = (String) properties.get("command");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
