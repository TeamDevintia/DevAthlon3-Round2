package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.bungee.network.NetHandlerInbound;
import io.github.teamdevintia.round2.bungee.network.NetHandlerOutbound;
import io.github.teamdevintia.round2.network.INetHandlerInbound;
import io.github.teamdevintia.round2.network.INetHandlerOutbound;
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

    @Override
    public void onLoad() {

        INetHandlerInbound netHandlerInbound = new NetHandlerInbound();
        INetHandlerOutbound netHandlerOutbound = new NetHandlerOutbound();


        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
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
            //   API.addServer(subdomain,"localhost");
        }
    }
}
