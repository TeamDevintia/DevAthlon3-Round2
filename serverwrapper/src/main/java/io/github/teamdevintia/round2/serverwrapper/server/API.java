package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;

/**
 * End point for the networking stuff, easy access to wrapper functionality
 *
 * @author MiniDigger
 */
@Log
public class API {

    private static int nextPort = 40000;

    /**
     * Starts a the server with that name, version and mod. Creates a new server if necessary
     *
     * @param name    the name of the server
     * @param mod     the mod that that server should be running
     * @param version the version that that server should be running
     * @return the port used, -1 if something went wrong
     */
    public static int startServer(String name, ServerMod mod, ServerVersion version) {
        // attempt to restart if params match
        Server server = ServerWrapper.getInstance().getServer(name);
        if (server != null) {
            if (server.getServerMod() == mod && server.getServerVersion() == version) {
                if (!server.isRunning()) {
                    try {
                        server.start();
                        return server.getServerPort();
                    } catch (ServerJarNotFoundException | IOException e) {
                        log.log(Level.WARNING, "Could not restart server", e);
                        return -1;
                    }
                }
            }
            return -1;
        }

        // we need a new one

        // new port
        nextPort++;

        // find a free port
        while (ServerWrapper.getInstance().getServer(nextPort) == null) {
            nextPort++;
        }

        server = new ServerBuilder(name).mod(mod).version(version).port(nextPort).javaOps(new ServerJavaOps(1024, true)).build();
        ServerWrapper.getInstance().addServer(server);
        try {
            server.start();
        } catch (ServerJarNotFoundException | IOException e) {
            log.log(Level.WARNING, "Could not start server", e);
        }

        return nextPort;
    }

    /**
     * Stops a server with that name
     *
     * @param name the name of the server to stop
     */
    public static void stopServer(String name) {
        Server server = ServerWrapper.getInstance().getServer(name);
        if (server == null) {
            return;
        }

        if (server.getThread() != null) {
            server.getThread().stopServer();
        }
    }

    /**
     * Stops a server with that port
     *
     * @param port the port of the server to stop
     */
    public static void stopServer(int port) {
        Server server = ServerWrapper.getInstance().getServer(port);
        if (server == null) {
            return;
        }

        if (server.getThread() != null) {
            server.getThread().stopServer();
        }
    }

    /**
     * Deletes a sever with that name
     *
     * @param name the name of the server to be deleted
     */
    public static void deleteServer(String name) {
        Server server = ServerWrapper.getInstance().getServer(name);
        if (server == null) {
            return;
        }

        ServerWrapper.getInstance().removeServer(server);

        if (!server.delete()) {
            log.warning("Could not delete server in the first try, retry in a few seconds!");

            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignored) {
                    }
                    server.delete();
                }
            }.start();
        }
    }

    /**
     * Deletes a sever with that port
     *
     * @param port the port of the server to be deleted
     */
    public static void deleteServer(int port) {
        Server server = ServerWrapper.getInstance().getServer(port);
        if (server == null) {
            return;
        }

        ServerWrapper.getInstance().removeServer(server);

        server.delete();
    }
}
