package io.github.teamdevintia.round2.serverwrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

/**
 * Created by Martin on 23.07.2016.
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

    public File getFullFile() {
        return new File(ServerWrapper.getInstance().getRoot(), file.getName());
    }
}
