package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 23.07.2016.
 */
public class ServerJarManager {

    private File repo;
    private List<ServerJar> supportedJars = new ArrayList<>();

    public ServerJarManager(File repo) {
        this.repo = repo;
    }

    public void init() {
        supportedJars.add(new ServerJar(new File("spigot-1.10.jar"), ServerMod.SPIGOT, ServerVersion.v1_10_R1, false));
    }

    public long checkAvailability() {
        return supportedJars.stream().filter(serverJar -> new File(repo, serverJar.getFile().getName()).exists()).peek(serverJar -> serverJar.setAvailable(true)).count();
    }

    public File getRepo() {
        return repo;
    }

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
