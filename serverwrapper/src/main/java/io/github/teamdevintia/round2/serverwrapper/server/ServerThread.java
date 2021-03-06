package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.network.packet.StoppedServerPacket;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.*;
import java.util.logging.Level;

/**
 * A thread for a server instance. handles in and output
 *
 * @author MiniDigger
 */
@Log
public class ServerThread extends Thread {

    private ProcessBuilder builder;
    private Server server;
    private Process process;
    private ServerThreadCallback callback;
    private PrintWriter pw;

    /**
     * Starts the server thread
     *
     * @param server   the server to start
     * @param pb       the process builder which process should be started
     * @param callback the callback that gets called if this threads stops (and the underlying server)
     */
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
            log.info("Starting server " + server.getName());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not start server " + server.getName() + "!", e);
        }

        // redirect input and error to sout and serr
        new Thread(new StreamRedirector(process.getInputStream(), System.out, server.getName())).start();
        new Thread(new StreamRedirector(process.getErrorStream(), System.err, server.getName())).start();

        // start print writer to send commands
        pw = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));

        try {
            int result = process.waitFor();

            callback.call(server, result);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Server Thread for " + server.getName() + " got interrupted!", e);
        }
    }

    /**
     * Sends a message into the console of the server
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        try {
            pw.println(message);
            pw.flush();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error while sending command " + message + " to " + server.getName(), e);
        }
    }

    /**
     * Stops this server (send stop/end into console)
     */
    public void stopServer() {
        if (server.getServerMod() == ServerMod.BUNGEE) {
            sendMessage("end");
        } else {
            sendMessage("stop");
        }

        // send packet
        StoppedServerPacket packet = new StoppedServerPacket(server.getName());
        ServerWrapper.getInstance().sendPacket(packet);
    }

    /**
     * redirects some thream somewhere
     *
     * @author spigotmc, MiniDigger
     */
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
                    // don't spam the ocnsole with useless stuff
                    if (line.equals("") || line.equals(">")) continue;

                    if (ServerWrapper.getInstance().getCommandHandler() != null) {
                        if (ServerWrapper.getInstance().getCommandHandler().isAttached(prefix)) {
                            out.println(prefix + " " + line);
                        }
                    } else {
                        out.println(prefix + " " + line);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
