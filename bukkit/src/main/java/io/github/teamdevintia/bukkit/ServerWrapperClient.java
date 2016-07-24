package io.github.teamdevintia.bukkit;

import io.github.teamdevintia.round2.network.EnumPacketDirection;
import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.ClientNetHandler;
import io.github.teamdevintia.round2.network.packet.ComponentPacket;
import io.github.teamdevintia.round2.network.packet.ServerInfoPacket;
import io.github.teamdevintia.round2.network.pipeline.MessageSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * the bukkit endpoint, mostly used to send server info packets
 *
 * @author MiniDigger
 */
public final class ServerWrapperClient extends JavaPlugin {

    // TODO load these from somewhere
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8000;

    // reflection stuff
    private boolean reflectionActive = false;
    private String nmsVersion;
    private Class<?> minecraftServerClass;
    private Field recentTpsField;
    private Method getServerMethod;

    private EventBus eventBus;
    private ClientNetHandler clientNetHandler;

    private String serverName;

    @Override
    public void onEnable() {
        initReflection();

        serverName = System.getProperty("serverName");

        // init event bus
        eventBus = new EventBus();
        eventBus.registerEvent(new PacketEventHandler(new BukkitPacketListener(), () -> getLogger().info("Event called"), "BungeeEventHandler"));

        // init connection
        clientNetHandler = new ClientNetHandler(eventBus);
        clientNetHandler.establishConnection(IP, PORT, streamHandler -> {
            MessageSerializer messageSerializer = new MessageSerializer("information");
            messageSerializer.addProperty("numPlayers", 123).addProperty("maxPlayers", 234);
            messageSerializer.addProperty("currentRam", 1024).addProperty("maxRam", 3056);
            messageSerializer.addProperty("tps", 20).addProperty("motd", "A Minecraft Server");
            streamHandler.handlePacket(new ComponentPacket(EnumPacketDirection.GLOBAL, messageSerializer.serialize()));
        });

        // send server info
        new BukkitRunnable() {
            @Override
            public void run() {
                sendInfoPacket();
            }
        }.runTaskTimer(this, 20, 20);
    }

    /**
     * Constructs a new server info packet and sends it
     */
    private void sendInfoPacket() {
        ServerInfoPacket packet = new ServerInfoPacket(serverName, Bukkit.getOnlinePlayers().size(), Bukkit.getServer().getMaxPlayers(),
                Runtime.getRuntime().freeMemory(), Runtime.getRuntime().maxMemory(), getTps());
        clientNetHandler.addToSendQueue(packet);
    }

    @Override
    public void onDisable() {
        clientNetHandler.getStreamHandler().getChannel().disconnect();
    }

    /**
     * Resolves all classes, fields and methods used to get the tps
     */
    public void initReflection() {
        try {
            nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split(Pattern.quote("."))[3];
            minecraftServerClass = Class.forName("net.minecraft.server." + nmsVersion + ".MinecraftServer");
            recentTpsField = minecraftServerClass.getDeclaredField("recentTps");
            getServerMethod = minecraftServerClass.getMethod("getServer");
            reflectionActive = true;
        } catch (Exception ex) {
            getLogger().log(Level.WARNING, "Could not init refection.", ex);
        }
    }

    /**
     * @return the last tps
     */
    public double[] getTps() {
        if (reflectionActive) {
            try {
                Object minecraftServer = getServerMethod.invoke(null);
                Object result = recentTpsField.get(minecraftServer);
                if (result instanceof double[]) {
                    return (double[]) result;
                }
            } catch (Exception e) {
                getLogger().log(Level.WARNING, "Could not get tps.", e);
            }
            getLogger().warning("Could not get tps.");
        }
        return new double[]{-1, -1, -1};
    }
}
