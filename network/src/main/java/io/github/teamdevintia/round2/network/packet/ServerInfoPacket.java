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
import java.util.Arrays;
import java.util.regex.Pattern;

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
        MessageSerializer messageSerializer = new MessageSerializer("serverInfo");
        messageSerializer.addProperty("name", name).addProperty("numPlayer", numPlayer)
                .addProperty("maxPlayer", maxPlayer).addProperty("freeRam", freeRam)
                .addProperty("maxRam", maxPlayer).addProperty("tps", tps[0] + ";" + tps[1] + ";" + tps[2]);

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
            MessageDeserializer messageDeserializer = new MessageDeserializer("serverInfo");
            JSONObject properties = messageDeserializer.deserialize(new String(messageBytes, Charset.forName("UTF-8")));

            this.name = (String) properties.get("name");
            this.numPlayer = (int) ((long) properties.get("numPlayer"));
            this.maxPlayer = (int) ((long) properties.get("maxPlayer"));
            this.freeRam = ((long) properties.get("freeRam"));
            this.maxRam = ((long) properties.get("maxRam"));

            String[] tempTPS = ((String) properties.get("tps")).split(";");
            tps = new double[3];
            tps[0] = Double.parseDouble(tempTPS[0]);
            tps[1] = Double.parseDouble(tempTPS[1]);
            tps[2] = Double.parseDouble(tempTPS[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
