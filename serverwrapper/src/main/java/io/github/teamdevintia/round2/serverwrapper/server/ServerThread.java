package io.github.teamdevintia.round2.serverwrapper.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.*;
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
            System.out.println(builder.command());
            log.info("Starting server " + server.getName());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not start server " + server.getName() + "!", e);
        }

        new Thread(new StreamRedirector(process.getInputStream(), System.out, server.getName())).start();
        new Thread(new StreamRedirector(process.getErrorStream(), System.err, server.getName())).start();

        try {
            int result = process.waitFor();

            callback.call(server, result);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Server Thread for " + server.getName() + " got interrupted!", e);
        }
    }

    public void sendMessage(String message) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(process.getOutputStream()))) {
            pw.println(message);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while sending command " + message + " to " + server.getName(), e);
        }
    }

    public void stopServer() {
        sendMessage("stop");
    }

    @RequiredArgsConstructor
    private static class StreamRedirector implements Runnable {

        private final InputStream in;
        private final PrintStream out;
        private final String prefix;

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    out.println(prefix + " " + line);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
