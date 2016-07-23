package io.github.teamdevintia.round2.serverwrapper.server;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The main handler class. manages all the stuff
 *
 * @author MiniDigger
 */
@Log
@Getter
public class ServerWrapper {

    private static ServerWrapper INSTANCE;
    //TODO we need to remove the servers at some point
    private List<Server> servers;
    private ServerJarManager jarManager;
    private File root;

    public ServerWrapper() {
        INSTANCE = this;
    }

    /**
     * initialises this wrapper
     *
     * @param root the root folder of this wrapper. expects a repo subfolder. every server will be a subfolder too
     */
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

    /**
     * Stops all running servers
     */
    public void stopServers() {
        servers.stream().filter(Server::isRunning).forEach(server -> server.getThread().stopServer());
    }

    /**
     * registers a new server
     *
     * @param server the server to register
     */
    public void addServer(Server server) {
        servers.add(server);
    }

    /**
     * @return The instance of this wrapper. it is a singleton
     */
    public static ServerWrapper getInstance() {
        return INSTANCE;
    }
}
