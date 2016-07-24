package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.packet.StartServerPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
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

    // TODO load these from somewhere
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8000;

    private static Main instance;

    private EventBus eventBus;
    private ClientNetHandler clientNetHandler;

    @Override
    public void onEnable() {
        instance = this;

        // init event bus
        eventBus = new EventBus();
        eventBus.registerEvent(new PacketEventHandler(new BungeePacketListener(), () -> getLogger().info("Event called"), "BungeeEventHandler"));

        // init connection
        clientNetHandler = new ClientNetHandler(eventBus);
        clientNetHandler.establishConnection(IP, PORT, streamHandler -> getLogger().info("Channel established"));

        // register event
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        clientNetHandler.getStreamHandler().getChannel().disconnect();
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        ServerPing response = event.getResponse();

        if (event.getConnection().getVirtualHost() == null) return;

        String subdomain = event.getConnection().getVirtualHost().getHostName().split(Pattern.quote("."))[0];
        ServerInfo info = ProxyServer.getInstance().getServerInfo(subdomain);
        if (info == null) {
            response.setDescriptionComponent(new TextComponent("Join to create a new server!"));
        } else {
            response.setDescriptionComponent(new TextComponent("Server is up, come on in!"));
        }

        event.setResponse(response);
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
            //  sendStartServerPacket(subdomain);

            System.out.println("lets sleep for a bit");
            // wait till new server is started up
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("done sleeping");
        }
    }

    private void sendStartServerPacket(String name) {
        StartServerPacket packet = new StartServerPacket(name);
        clientNetHandler.addToSendQueue(packet);
    }

    public static Main getInstance() {
        return instance;
    }
}
