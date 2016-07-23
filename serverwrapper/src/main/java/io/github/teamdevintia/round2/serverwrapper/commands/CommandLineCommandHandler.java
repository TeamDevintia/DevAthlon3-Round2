package io.github.teamdevintia.round2.serverwrapper.commands;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import io.github.teamdevintia.round2.serverwrapper.placeholder.PlaceHolderBuilder;
import io.github.teamdevintia.round2.serverwrapper.server.*;
import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * Handles the commands for interactive mode
 *
 * @author MiniDigger
 */
@Log
public class CommandLineCommandHandler {

    //TODO proper command system

    public CommandLineCommandHandler() {
        run();
    }

    /**
     * Main method, halts the main thread
     */
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                String[] args = buffer.split(" ");
                switch (args[0]) {
                    case "startdevserver":
                        Server server = new ServerBuilder("DevServer").port(3333).javaOps(new ServerJavaOps(1024, false)).mod(ServerMod.SPIGOT).version(ServerVersion.v1_10_R1).build();
                        try {
                            server.start();
                        } catch (ServerJarNotFoundException e) {
                            log.log(Level.ALL, "Could not find server jar", e);
                        }
                        break;
                    case "mcsignondoor":
                        new PlaceHolderBuilder().port(40000).motd("Gone Fishin' Back in Five Minutes").numPlayer(0).maxPlayers(0).
                                version("Offline").awayMessage("Gone Fishin' Back in Five Minutes").protocol(47).build();
                        break;
                    case "stop":
                        log.info("stopping servers");
                        ServerWrapper.getInstance().stopServers();
                        // System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException ex) {
            log.log(Level.ALL, "Could not handle interactive mode", ex);
        }
    }
}
