package io.github.teamdevintia.round2.serverwrapper.server;

import java.io.File;

/**
 * A chainable builder to create a {@link Server}
 *
 * @author MiniDigger
 */
public class ServerBuilder {

    private int serverPort = 25565;
    private String name;
    private File serverFolder;
    private ServerVersion serverVersion;
    private ServerMod serverMod;
    private ServerJavaOps serverJavaOps;

    /**
     * Starts the chain
     *
     * @param name the name of the server
     */
    public ServerBuilder(String name) {
        this.name = name;
        this.serverFolder = new File(ServerWrapper.getInstance().getRoot(), name);
    }

    /**
     * @param port the port the server should use
     */
    public ServerBuilder port(int port) {
        this.serverPort = port;
        return this;
    }

    /**
     * @param serverFolder the root folder of the server
     */
    public ServerBuilder folder(File serverFolder) {
        this.serverFolder = serverFolder;
        return this;
    }

    /**
     * @param version the minecraft nms version of the server
     */
    public ServerBuilder version(ServerVersion version) {
        this.serverVersion = version;
        return this;
    }

    /**
     * @param mod the server mod of the server
     */
    public ServerBuilder mod(ServerMod mod) {
        this.serverMod = mod;
        return this;
    }

    /**
     * @param ops the java startup parameters of the server
     */
    public ServerBuilder javaOps(ServerJavaOps ops) {
        this.serverJavaOps = ops;
        return this;
    }

    /**
     * Builds the server from the params entered. May throw a IllegalStateException if some params are missing
     *
     * @return the builded server
     */
    public Server build() {
        if (serverVersion == null) throw new IllegalStateException("version needs to be set!");
        if (serverMod == null) throw new IllegalStateException("mod needs to be set!");
        if (serverJavaOps == null) throw new IllegalStateException("javaOps needs to be set!");

        return new Server(serverPort, name, serverFolder, serverVersion, serverMod, serverJavaOps, null, false);
    }
}
