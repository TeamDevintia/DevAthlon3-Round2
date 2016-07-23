package io.github.teamdevintia.round2.serverwrapper.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

/**
 * Represents a server jar
 *
 * @author MiniDigger
 */
@Getter
@AllArgsConstructor
public class ServerJar {

    private File file;
    private ServerMod mod;
    private ServerVersion version;
    private boolean available;

    @Override
    public String toString() {
        return mod.name() + "-" + version.name();
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
