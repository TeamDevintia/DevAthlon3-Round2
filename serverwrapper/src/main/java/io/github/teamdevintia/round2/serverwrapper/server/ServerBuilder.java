package io.github.teamdevintia.round2.serverwrapper.server;

import java.io.File;

/**
 * Created by Martin on 23.07.2016.
 */
public class ServerBuilder {

    private int serverPort = 25565;
    private String name;
    private File serverFolder;
    private ServerVersion serverVersion;
    private ServerMod serverMod;
    private ServerJavaOps serverJavaOps;

    public ServerBuilder(String name) {
        this.name = name;
        this.serverFolder = new File(ServerWrapper.getInstance().getRoot(), name);
    }

    public ServerBuilder port(int port) {
        this.serverPort = port;
        return this;
    }

    public ServerBuilder folder(File serverFolder) {
        this.serverFolder = serverFolder;
        return this;
    }

    public ServerBuilder version(ServerVersion version) {
        this.serverVersion = version;
        return this;
    }

    public ServerBuilder mod(ServerMod mod) {
        this.serverMod = mod;
        return this;
    }

    public ServerBuilder javaOps(ServerJavaOps ops) {
        this.serverJavaOps = ops;
        return this;
    }

    public Server build() {
        if (serverVersion == null) throw new IllegalStateException("version needs to be set!");
        if (serverMod == null) throw new IllegalStateException("mod needs to be set!");
        if (serverJavaOps == null) throw new IllegalStateException("javaOps needs to be set!");

        return new Server(serverPort, name, serverFolder, serverVersion, serverMod, serverJavaOps, null, false);
    }
}
