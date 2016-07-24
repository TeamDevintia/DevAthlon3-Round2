package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import io.github.teamdevintia.round2.network.EnumPacketDirection;
import io.github.teamdevintia.round2.network.pipeline.MessageSerializer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.regex.Pattern;

/**
 * @author Shad0wCore
 *         <p>
 *         Represents the main class for the stuff working with BungeeCord
 */
public class Main extends Plugin implements Listener {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 8000;

    private EventBus eventBus;
    private ClientNetHandler clientNetHandler;

    @Override
    public void onEnable() {
        // init event bus
        eventBus = new EventBus();
        eventBus.registerEvent(new PacketEventHandler(new BungeePacketListener(), () -> getLogger().info("Event called"), "BungeeEventHandler"));

        // init connection
        clientNetHandler = new ClientNetHandler(eventBus);
        clientNetHandler.establishConnection(IP, PORT, streamHandler -> {
            MessageSerializer messageSerializer = new MessageSerializer("information");
            messageSerializer.addProperty("numPlayers", 123).addProperty("maxPlayers", 234);
            messageSerializer.addProperty("currentRam", 1024).addProperty("maxRam", 3056);
            messageSerializer.addProperty("tps", 20).addProperty("motd", "A Minecraft Server");
            streamHandler.handlePacket(new ComponentPacket(EnumPacketDirection.GLOBAL, messageSerializer.serialize()));
        });

        // register event
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        //TODO shutdown connection
        super.onDisable();
    }

    @EventHandler
    public void postLogin(PostLoginEvent event) {
        String subdomain = event.getPlayer().getPendingConnection().getVirtualHost().getHostName().split(Pattern.quote("."))[0];
        ServerInfo info = ProxyServer.getInstance().getServerInfo(subdomain);
        if (info != null) {
            // we got a server already
            System.out.println("we got a server with " + subdomain + ", go connect to that");
            event.getPlayer().connect(info);
        } else {
            // we need to start a new one!
            // TODO join to a new server
            System.out.println("we need to spin up a new server " + subdomain + ", you gotta wait");
            sendStartServerPacket(subdomain);
        }
    }

    private void sendStartServerPacket(String name) {
//        clientNetHandler.getStreamHandler().handlePacket(packet);
    }
}
