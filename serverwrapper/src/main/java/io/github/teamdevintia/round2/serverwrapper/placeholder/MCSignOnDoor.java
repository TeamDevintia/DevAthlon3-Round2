package io.github.teamdevintia.round2.serverwrapper.placeholder;

import lombok.extern.java.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;

/**
 * handles server pings if the actual server is offline.<br>
 * original version: https://bukkit.org/threads/admin-mcsignondoor-1-9-so-your-server-can-say-gone-fishin-back-in-five.8814/
 *
 * @author MiniDigger
 */
@Log
public class MCSignOnDoor {

    private int serverPort;
    private ServerSocket serverSocket;

    private String reportedVersionNumber, awayMessage, motdMessage, numplayers, maxplayers;

    public MCSignOnDoor(int port, String awayMessage, String numPlayers, String maxPlayers) {
        this.serverPort = port;
        this.awayMessage = awayMessage;
        this.numplayers = numPlayers;
        this.maxplayers = maxPlayers;
        motdMessage = "Server is offline";
        reportedVersionNumber = "Offline";
    }

    public MCSignOnDoor(int port, String awayMessage, String numPlayers, String maxPlayers, String reportedVersionNumber, String motdMessage) {
        this(port, awayMessage, numPlayers, maxPlayers);
        this.motdMessage = motdMessage;
        this.reportedVersionNumber = reportedVersionNumber;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException while starting server socket!", e);
        }
        while (!serverSocket.isClosed()) {
            try {
                Socket s = serverSocket.accept();
                log.info("Received connection from " + s.getInetAddress().getHostAddress());
                new ResponderThread(s, awayMessage, numplayers, maxplayers, reportedVersionNumber, motdMessage).start();
            } catch (SocketException ex) {
                if (!serverSocket.isClosed()) //if the socket is just closing, ignore this
                    log.log(Level.SEVERE, "SocketException while accepting client!", ex);
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException while accepting client!", e);
            }
        }
    }

    public void stop() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
