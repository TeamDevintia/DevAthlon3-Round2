package io.github.teamdevintia.round2.serverwrapper.placeholder;

import java.io.*;
import java.net.Socket;

/**
 * Created by Martin on 23.07.2016.
 */
public class ResponderThread extends Thread {

    String version = "1.8.7";
    int protocol = 47;
    int maxPlayer = 0;
    int numPlayer = 0;
    String motd = "Hello World";
    Socket socket;

    public ResponderThread(Socket s, String awayMessage, String numplayers, String maxplayers, String reportedVersionNumber, String motdMessage) {
        socket = s;
    }

    public void run() {
        try {
            // open connection
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            DataInputStream in = new DataInputStream(inputStream);

            byte status = in.readByte();
            System.out.println("status = " + status);

            // handle client handshake
            // packet id 0x00
            // varInt - client version
            // String - server address used to connect
            // Unsigned Short - port
            // varInt - nextstats, should be 1
            System.out.println("-----handle handshake");
            byte packedId = in.readByte();
            System.out.println("packedId = " + packedId);
            int protocolversion = readVarInt(in);
            System.out.println("protocolversion = " + protocolversion);
            byte size = in.readByte(); // size of addr
            // String addr = in.skipBytes(size);
            in.skipBytes(size); // fuck you addr
            //System.out.println("addr = " + addr);
            int port = in.readUnsignedShort();
            System.out.println("port = " + port);
            int nextState = readVarInt(in);
            System.out.println("nextState = " + nextState);
            System.out.println("-----done");

            // handle request
            // read size
            // packet id 0x00, no fields
            System.out.println("-----handle request");
            size = in.readByte();
            System.out.println("size = " + size);
            packedId = in.readByte();
            System.out.println("packedId = " + packedId);
            System.out.println("-----done");

            // send response to that
            // packet id 0x00
            // byte lenght
            // string json
            String response = "{\"version\":{\"name\":\"" + version + "\",\"protocol\":" + protocol + "},\"players\":{\"max\":"
                    + maxPlayer + ",\"online\":" + numPlayer + ",\"sample\":[]},\"description\":{\"text\":\"" + motd + "\"}}";
            System.out.println("-----send response");
            out.writeByte(response.length() + 2);
            out.writeByte(0); // packet id

            System.out.println("response = " + response);
            out.writeByte(response.length());
            out.writeBytes(response);
            System.out.println("-----done");

            // handle ping
            // packet id 0x01
            // long - random payload, who cares
            System.out.println("-----handle ping");
            packedId = in.readByte();
            System.out.println("packedId = " + packedId);
            long payload = in.readLong();
            System.out.println("payload = " + payload);
            System.out.println("-----done");

            // send pong
            // packet id 0x01
            // long - same payload
            System.out.println("-----send pong");
            out.writeByte(1); // packet id
            out.writeLong(payload);
            System.out.println("-----done");

            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int readVarInt(DataInputStream in) throws IOException {
        int i = 0;
        int j = 0;
        while (true) {
            int k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
            if ((k & 0x80) != 128) break;
        }
        return i;
    }

    private void writeVarInt(DataOutputStream out, int paramInt) throws IOException {
        while (true) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }

            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }
}
