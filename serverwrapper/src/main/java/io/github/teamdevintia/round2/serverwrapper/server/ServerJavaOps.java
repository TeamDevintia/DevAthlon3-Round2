package io.github.teamdevintia.round2.serverwrapper.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 23.07.2016.
 */
public class ServerJavaOps {

    private int ram;
    private boolean useAikarFlags;

    public ServerJavaOps(int ram, boolean useAikarFlags) {
        this.ram = ram;
        this.useAikarFlags = useAikarFlags;
    }

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
