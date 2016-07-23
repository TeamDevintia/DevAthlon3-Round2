package io.github.teamdevintia.round2.serverwrapper;

import lombok.extern.java.Log;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by Martin on 23.07.2016.
 */
@Log
public class ServerThread extends Thread {

    private ProcessBuilder builder;
    private Server server;
    private Process process;
    private ServerThreadCallback callback;

    public ServerThread(Server server, ProcessBuilder pb, ServerThreadCallback callback) {
        this.server = server;
        this.builder = pb;
        this.callback = callback;

        setName("ServerThread#" + server.getName());
        start();
    }

    @Override
    public void run() {
        try {
            process = builder.start();
        } catch (IOException e) {
            log.log(Level.ALL, "Could not start server " + server.getName() + "!", e);
        }

        try {
            int result = process.waitFor();

            callback.call(server, result);
        } catch (InterruptedException e) {
            log.log(Level.ALL, "Server Thread for " + server.getName() + " got interrupted!", e);
        }
    }
}
