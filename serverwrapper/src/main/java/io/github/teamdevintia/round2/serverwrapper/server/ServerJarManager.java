package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * manages the repo with all server jars
 *
 * @author MiniDigger
 */
public class ServerJarManager {

    private File repo;
    private List<ServerJar> supportedJars = new ArrayList<>();

    /**
     * consturcts a new ServerJarManager
     *
     * @param repo the repo this ServerJarManager should use
     */
    public ServerJarManager(File repo) {
        this.repo = repo;
    }

    /**
     * Adds all supported jars to a list. We don't support untested jars!
     */
    public void init() {
        supportedJars.add(new ServerJar(new File("spigot-1.10.jar"), ServerMod.SPIGOT, ServerVersion.v1_10_R1, false));

        // always use latest bungee, it is backwards compatible
        supportedJars.add(new ServerJar(new File("BungeeCord.jar"), ServerMod.BUNGEE, ServerVersion.v1_10_R1, false));
    }

    /**
     * Checks if all supported jars are found in the local repo
     *
     * @return the amount of jars that were found in the repo and are useable
     */
    public long checkAvailability() {
        return supportedJars.stream().filter(serverJar -> new File(repo, serverJar.getFile().getName()).exists()).peek(serverJar -> serverJar.setAvailable(true)).count();
    }

    /**
     * @return the repo for this jar manager. the repo is the folder where all jars are located
     */
    public File getRepo() {
        return repo;
    }

    /**
     * Gets a jar from a server mod and a version
     *
     * @param serverMod     the server mod that should be used
     * @param serverVersion the server version that should be used
     * @return the jar with the mod and version
     * @throws ServerJarNotFoundException if no jar with that mod and version could be found
     */
    public ServerJar getJar(ServerMod serverMod, ServerVersion serverVersion) throws ServerJarNotFoundException {
        for (ServerJar jar : supportedJars) {
            if (jar.getMod().equals(serverMod) && jar.getVersion().equals(serverVersion)) {
                return jar;
            }
        }

        throw new ServerJarNotFoundException(serverMod, serverVersion);
    }

    public File getFile(ServerJar jar) {
        return new File(repo, jar.getFile().getName());
    }
}
