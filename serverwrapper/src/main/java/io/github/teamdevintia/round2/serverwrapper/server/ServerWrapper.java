package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.network.internal.EventBus;
import io.github.teamdevintia.round2.network.internal.PacketEventHandler;
import io.github.teamdevintia.round2.network.internal.handlers.WrapperServerNetHandler;
import io.github.teamdevintia.round2.serverwrapper.commands.CommandLineCommandHandler;
import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import io.github.teamdevintia.round2.serverwrapper.placeholder.PlaceHolderHandler;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * The main handler class. manages all the stuff
 *
 * @author MiniDigger
 */
@Log
@Getter
public class ServerWrapper {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 8000;

    private static ServerWrapper INSTANCE;
    private Server bungee;
    private List<Server> servers;
    private ServerJarManager jarManager;
    private PlaceHolderHandler placeHolderHandler;
    private CommandLineCommandHandler commandHandler;
    private File root;
    private boolean autoAttach;

    public ServerWrapper() {
        if (INSTANCE != null) {
            throw new IllegalStateException("ServerWrapper is a singleton!");
        }
        INSTANCE = this;
    }

    /**
     * initialises this wrapper
     *
     * @param root       the root folder of this wrapper. expects a repo subfolder. every server will be a subfolder too (unless
     * @param autoAttach if true,
     */
    public void init(File root, boolean autoAttach) {
        this.root = root;
        this.autoAttach = autoAttach;
        servers = new ArrayList<>();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                stopServers();
                placeHolderHandler.removeAll();
                try {
                    Thread.sleep(0b1111101000);
                } catch (InterruptedException ignore) {
                }
            }
        });

        // load jars
        jarManager = new ServerJarManager(new File(root, "repo"));
        jarManager.init();
        long loaded = jarManager.checkAvailability();
        log.info("Found " + loaded + " ServerJars in repo " + jarManager.getRepo());

        // init placeholders
        placeHolderHandler = new PlaceHolderHandler();

        // setup packet event bus
        EventBus eventBus = new EventBus();
        eventBus.registerEvent(new PacketEventHandler(new ServerWrapperPacketListener(), () -> log.info("Event called"), "ServerWrapperEventHandler"));

        // startup netty
        WrapperServerNetHandler wrapperServerNetHandler = new WrapperServerNetHandler(eventBus);
        wrapperServerNetHandler.establishServerConnection(IP, PORT, streamHandler -> log.info("Channel established"));

        // start bungee
        try {
            bungee = Server.getBungee();
            bungee.start();
        } catch (ServerJarNotFoundException | IOException e) {
            log.log(Level.SEVERE, "Could not start bungee!", e);
        }
    }

    /**
     * Stops all running servers
     */
    public void stopServers() {
        bungee.getThread().stopServer();

        servers.stream().filter(Server::isRunning).forEach(server -> server.getThread().stopServer());
    }

    /**
     * Gets a server by its name
     *
     * @param name the name of the server
     * @return the server with that name, null if not found
     */
    public Server getServer(String name) {
        for (Server server : servers) {
            if (server.getName().equals(name)) {
                return server;
            }
        }
        return null;
    }

    /**
     * Gets a server by its port
     *
     * @param port the port of the server
     * @return the server with that port, null if not found
     */
    public Server getServer(int port) {
        for (Server server : servers) {
            if (server.getServerPort() == port) {
                return server;
            }
        }
        return null;
    }

    /**
     * Removes a server from the list
     *
     * @param server the server to be removed
     */
    public void removeServer(Server server) {
        servers.remove(server);
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


    /**
     * @return the command handler for this wrapper
     */
    public CommandLineCommandHandler getCommandHandler() {
        return commandHandler;
    }

    /**
     * @param commandHandler the commandline handler this wrapper should use
     */
    public void setCommandHandler(CommandLineCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
}
