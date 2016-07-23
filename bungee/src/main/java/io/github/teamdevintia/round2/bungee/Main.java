package io.github.teamdevintia.round2.bungee;

import io.github.teamdevintia.round2.bungee.network.NetHandlerInbound;
import io.github.teamdevintia.round2.bungee.network.NetHandlerOutbound;
import io.github.teamdevintia.round2.network.INetHandlerInbound;
import io.github.teamdevintia.round2.network.INetHandlerOutbound;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author Shad0wCore
 *         <p>
 *         Represents the main class for the stuff working with BungeeCord
 */
public class Main extends Plugin {

    @Override
    public void onLoad() {

        INetHandlerInbound netHandlerInbound = new NetHandlerInbound();
        INetHandlerOutbound netHandlerOutbound = new NetHandlerOutbound();

        

    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
