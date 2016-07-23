package io.github.teamdevintia.round2.serverwrapper.server;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 23.07.2016.
 */
@Log
@Getter
public class ServerWrapper {

    private static ServerWrapper INSTANCE;

    private List<Server> servers;
    private ServerJarManager jarManager;
    private File root;

    public ServerWrapper() {
        INSTANCE = this;
    }

    public void init(File root) {
        this.root = root;
        servers = new ArrayList<>();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stopServers();
            }
        });

        // load jars
        jarManager = new ServerJarManager(new File(root, "repo"));
        jarManager.init();
        long loaded = jarManager.checkAvailability();
        log.info("Found " + loaded + " ServerJars in repo " + jarManager.getRepo());
    }

    public void stopServers() {
        servers.stream().filter(Server::isRunning).forEach(server -> server.getThread().stopServer());
    }

    public void addServer(Server server) {
        servers.add(server);
    }

    public static ServerWrapper getInstance() {
        return INSTANCE;
    }
}
