package io.github.teamdevintia.round2.serverwrapper.commands;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import io.github.teamdevintia.round2.serverwrapper.placeholder.PlaceHolderBuilder;
import io.github.teamdevintia.round2.serverwrapper.server.*;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Handles the commands for interactive mode
 *
 * @author MiniDigger
 */
@Log
public class CommandLineCommandHandler {

    private Map<String, Command> commands = new HashMap<>();
    private List<String> attachedServers = new ArrayList<>();

    public CommandLineCommandHandler() {
        addCommands();
        run();
    }

    /**
     * Creates the commands
     */
    private void addCommands() {
        commands.put("stop", (args) -> {
            log.info("stopping servers");
            ServerWrapper.getInstance().stopServers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            System.exit(0);
        });

        commands.put("stopserver", (args -> {
            if (args.length == 1) {
                log.warning("Missing arg <port/name>");
                return;
            }

            // try to parse the port
            Server server = getServerUsingPortOrName(args[1]);

            if (server == null) {
                log.warning("Unknown server " + args[1]);
                return;
            }

            if (!server.isRunning()) {
                log.warning("Server is not running");
                return;
            }

            server.getThread().stopServer();
            log.info("Server stopped");
        }));

        commands.put("startserver", args -> {
            if (args.length == 1) {
                log.warning("Missing arg <port/name>");
                return;
            }

            // try to parse the port
            Server server = getServerUsingPortOrName(args[1]);

            if (server == null) {
                log.warning("Unknown server " + args[1]);
                return;
            }

            if (server.isRunning()) {
                log.warning("Server is running!");
                return;
            }

            try {
                server.start();
                log.info("Server started");
            } catch (ServerJarNotFoundException | IOException e) {
                log.log(Level.WARNING, "Error while starting the server", e);
            }
        });

        commands.put("createserver", args -> {
            if (args.length == 1) {
                log.warning("Missing arg <name>");
                return;
            }
            int p = API.startServer(args[1], ServerMod.SPIGOT, ServerVersion.v1_10_R1);

            if (p == -1) {
                log.info("Error while starting server");
            } else {
                log.info("Server started");
            }
        });

        commands.put("deleteserver", args -> {
            if (args.length == 1) {
                log.warning("Missing arg <name>");
                return;
            }

            API.stopServer(args[1]);
            API.deleteServer(args[1]);

            log.info("Server deleted");
        });

        commands.put("attach", args -> {
            if (args.length == 1) {
                log.warning("Missing arg <prefix>");
                return;
            }

            attachedServers.add(args[1]);
            log.info("Attached to session " + args[1]);
        });

        commands.put("detach", args -> {
            if (args.length == 1) {
                log.warning("Missing arg <prefix>");
                return;
            }

            attachedServers.remove(args[1]);
            log.info("detach from session " + args[1]);
        });

        commands.put("mcsignondoor", args -> {
            new PlaceHolderBuilder().port(40000).motd("Gone Fishin' Back in Five Minutes").numPlayer(0).maxPlayers(0).
                    version("Offline").awayMessage("Gone Fishin' Back in Five Minutes").protocol(47).build();
        });
    }

    /**
     * Main method, halts the main thread
     */
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                String[] args = buffer.split(" ");
                Command cmd = commands.get(args[0]);
                if (cmd == null) {
                    log.warning("Command not found");
                    continue;
                }

                cmd.execute(args);
            }
        } catch (IOException ex) {
            log.log(Level.ALL, "Could not handle interactive mode", ex);
        }
    }

    /**
     * Retrieves a server using the port or the name specified
     *
     * @param portOrName a int as a string for a port or just a name
     * @return the server that matches the port or name, may be null
     */
    private Server getServerUsingPortOrName(String portOrName) {
        Server server;
        try {
            int port = Integer.parseInt(portOrName);
            server = ServerWrapper.getInstance().getServer(port);
        } catch (NumberFormatException ex) {
            // use the name then
            server = ServerWrapper.getInstance().getServer(portOrName);
        }

        return server;
    }

    /**
     * Checks if a server session is attached and should be printed to the console
     *
     * @param prefix the prefix of the server session
     * @return if the server session is attached and should be printed to the console
     */
    public boolean isAttached(String prefix) {
        return attachedServers.contains(prefix);
    }
}
