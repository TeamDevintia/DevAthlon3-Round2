package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.network.internal.PipelineEvent;
import io.github.teamdevintia.round2.network.internal.PipelineEventListener;
import io.github.teamdevintia.round2.network.internal.events.PacketReceiveEvent;
import io.github.teamdevintia.round2.network.packet.*;
import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Martin on 24.07.2016.
 */
@Log
public class ServerWrapperPacketListener implements PipelineEventListener {

    @PipelineEvent
    public void onReceive(PacketReceiveEvent event) {
        if (event.getReceivedPacket() instanceof CreateServerPacket) {
            CreateServerPacket packet = (CreateServerPacket) event.getReceivedPacket();
            if (packet.getPort() != -1) {
                try {
                    Server server = new ServerBuilder(packet.getName()).version(ServerVersion.valueOf(packet.getVersion())).mod(ServerMod.valueOf(packet.getMod()))
                            .port(packet.getPort()).javaOps(new ServerJavaOps(1024, true)).build();
                    ServerWrapper.getInstance().addServer(server);
                    server.start();
                } catch (ServerJarNotFoundException | IOException e) {
                    log.log(Level.WARNING, "Could not handle CreateServerPacket", e);
                }
            } else {
                API.startServer(packet.getName(), ServerMod.valueOf(packet.getMod()), ServerVersion.valueOf(packet.getVersion()));
            }
        } else if (event.getReceivedPacket() instanceof StartServerPacket) {
            StartServerPacket packet = (StartServerPacket) event.getReceivedPacket();
            Server server = ServerWrapper.getInstance().getServer(packet.getName());
            if (server == null) {
                log.warning("Requested to start server" + packet.getName() + " which doesn't exist!");
                return;
            }
            try {
                server.start();
            } catch (ServerJarNotFoundException | IOException e) {
                log.log(Level.WARNING, "Could not handle StartServerPacket", e);
            }
        } else if (event.getReceivedPacket() instanceof StopServerPacket) {
            StoppedServerPacket packet = (StoppedServerPacket) event.getReceivedPacket();
            Server server = ServerWrapper.getInstance().getServer(packet.getName());
            if (server == null) {
                log.warning("Requested to stop server" + packet.getName() + " which doesn't exist!");
                return;
            }
            if (server.getThread() == null) {
                log.warning("Requested to stop server " + packet.getName() + " which is not started!");
                return;
            }
            server.getThread().stopServer();
        } else if (event.getReceivedPacket() instanceof DeleteServerPacket) {
            DeleteServerPacket packet = (DeleteServerPacket) event.getReceivedPacket();
            Server server = ServerWrapper.getInstance().getServer(packet.getName());
            if (server == null) {
                log.warning("Requested to delete server" + packet.getName() + " which doesn't exist!");
                return;
            }
            server.delete();
        } else if (event.getReceivedPacket() instanceof ConsoleCommandPacket) {
            ConsoleCommandPacket packet = (ConsoleCommandPacket) event.getReceivedPacket();
            Server server = ServerWrapper.getInstance().getServer(packet.getServerName());
            if (server == null) {
                log.warning("Requested to send console command to server" + packet.getServerName() + " which doesn't exist!");
                return;
            }
            if (server.getThread() == null) {
                log.warning("Requested to snd console command to server " + packet.getServerName() + " which is not started!");
                return;
            }
            server.getThread().sendMessage(packet.getCommand());
        }
    }
}
