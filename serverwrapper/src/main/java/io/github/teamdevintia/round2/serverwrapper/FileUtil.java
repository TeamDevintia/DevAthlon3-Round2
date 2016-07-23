package io.github.teamdevintia.round2.serverwrapper;

import io.github.teamdevintia.round2.serverwrapper.server.ServerWrapper;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

/**
 * @author MiniDigger
 */
@Log
public class FileUtil {

    public static final File BUNGEE = new File(ServerWrapper.getInstance().getJarManager().getRepo(), "bungee.jar");
    public static final File BUKKIT = new File(ServerWrapper.getInstance().getJarManager().getRepo(), "bungee.jar");

    public static boolean copyPlugin(File jar, File dest) {
        try {
            Files.copy(jar.toPath(), new File(dest, jar.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not copy file from " + jar.getAbsolutePath() + " to " + dest.getAbsolutePath(), e);
            return false;
        }
    }
}
