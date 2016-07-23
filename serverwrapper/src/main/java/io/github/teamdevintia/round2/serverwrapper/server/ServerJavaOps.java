package io.github.teamdevintia.round2.serverwrapper.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all java startup options and flags.<br>
 * mainly ram, because there is no point in not used aikar's mcflags
 *
 * @author MiniDigger
 */
public class ServerJavaOps {

    private int ram;
    private boolean useAikarFlags;

    /**
     * @param ram           the amount of ram to use, in MB!
     * @param useAikarFlags if aikar's mc flags should be used
     */
    public ServerJavaOps(int ram, boolean useAikarFlags) {
        this.ram = ram;
        this.useAikarFlags = useAikarFlags;
    }

    /**
     * @return the builed flags
     */
    public List<String> getFlags() {
        List<String> flags = new ArrayList<>();

        flags.add("-Xms" + ram + "M");
        flags.add("-Xmx" + ram + "M");

        if (useAikarFlags) {
            // mcflags.emc.gs
            flags.add("-XX:+UseG1GC");
            flags.add("-XX:+UnlockExperimentalVMOptions");
            flags.add("-XX:MaxGCPauseMillis=100");
            flags.add("-XX:+DisableExplicitGC");
            flags.add("-XX:TargetSurvivorRatio=90");
            flags.add("-XX:G1NewSizePercent=50");
            flags.add("-XX:G1MaxNewSizePercent=80");
            flags.add("-XX:InitiatingHeapOccupancyPercent=10");
            flags.add("-XX:G1MixedGCLiveThresholdPercent=50");
            flags.add("-XX:+AggressiveOpts");
            flags.add("-XX:+AlwaysPreTouch");
        }

        return flags;
    }
}
