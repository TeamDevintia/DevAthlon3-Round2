package io.github.teamdevintia.round2.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.logging.Level;

/**
 * Allows to easily do stuff, like ading and removing servers
 *
 * @author MiniDigger
 */
public class API {

    /**
     * Adds a server to the proxy
     *
     * @param name the name of the server
     * @param host the host address of the server
     * @param port the port of the server
     * @return weather or not is was successful
     */
    public static boolean addServer(String name, String host, int port) {
        if (ProxyServer.getInstance().getServers().containsKey(name)) {
            return false;
        }
        ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, new InetSocketAddress(host, port), "", false);
        ProxyServer.getInstance().getServers().put(name, serverInfo);
        try {
            Configuration bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
            bungeeConfig.set("servers." + name + ".address", host + ":" + port);
            bungeeConfig.set("servers." + name + ".restricted", false);
            bungeeConfig.set("servers." + name + ".motd", "");
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(bungeeConfig, new File("config.yml"));
        } catch (Exception e) {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Could not add server to config", e);
            return false;
        }
        return true;
    }

    /**
     * Removes a server from the proxy
     *
     * @param name the name of the server to be removed
     * @return weather or not is was successful
     */
    public static boolean removeServer(String name) {
        if (!ProxyServer.getInstance().getServers().containsKey(name)) {
            return false;
        }
        for (final ProxiedPlayer player : ProxyServer.getInstance().getServers().get(name).getPlayers()) {
            player.disconnect(new ComponentBuilder("Server was stopped, please rejoin.").color(ChatColor.GRAY).create());
        }
        ProxyServer.getInstance().getServers().remove(name);
        try {
            Configuration bungeeConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
            bungeeConfig.set("servers." + name, null);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(bungeeConfig, new File("config.yml"));
        } catch (Exception e) {
            ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Could not remove server from config", e);
            return false;
        }
        return true;
    }
}
