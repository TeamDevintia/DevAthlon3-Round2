package io.github.teamdevintia.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.regex.Pattern;

public final class ServerWrapperClient extends JavaPlugin {

    private boolean reflectionActive = false;
    private String nmsVersion;
    private Class<?> minecraftServerClass;
    private Field recentTpsField;
    private Method getServerMethod;

    @Override
    public void onEnable() {
        initReflection();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("mytps")) {
            final StringBuilder sb = new StringBuilder(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: ");
            double[] recentTps;
            for (int length = (recentTps = getTps()).length, i = 0; i < length; ++i) {
                final double tps = recentTps[i];
                sb.append(this.format(tps));
                sb.append(", ");
            }
            sender.sendMessage(sb.substring(0, sb.length() - 2));
            return true;
        }
        return false;
    }

    private String format(final double tps) {
        return String.valueOf(((tps > 18.0) ? ChatColor.GREEN : ((tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED)).toString()) + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }

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
