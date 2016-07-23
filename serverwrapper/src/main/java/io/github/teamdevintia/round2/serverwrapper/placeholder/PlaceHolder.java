package io.github.teamdevintia.round2.serverwrapper.placeholder;

import io.github.teamdevintia.round2.serverwrapper.server.ServerWrapper;
import lombok.Getter;
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
@Getter
public class PlaceHolder extends Thread {

    private int serverPort;
    private ServerSocket serverSocket;

    private String reportedVersionNumber, awayMessage, motdMessage;
    private int numplayers, maxplayers, prot;

    /**
     * Constructs a new placeholder and starts it
     *
     * @param port        the port this placeholder should listen to
     * @param awayMessage the message that should be shown if a client tries to connect to this port
     * @param numPlayers  the number that should be shown as numPlayers in the ping response
     * @param maxPlayers  the number that should be shown as maxPlayers in the ping response
     */
    public PlaceHolder(int port, String awayMessage, int numPlayers, int maxPlayers) {
        this.serverPort = port;
        this.awayMessage = awayMessage;
        this.numplayers = numPlayers;
        this.maxplayers = maxPlayers;
        motdMessage = "Server is offline";
        reportedVersionNumber = "Offline";
        prot = 47;
        setName("PlaceHolder#" + serverPort);
        start();

        ServerWrapper.getInstance().getPlaceHolderHandler().add(this);
    }

    /**
     * Constructs a new placeholder and starts it
     *
     * @param port                  the port this placeholder should listen to
     * @param awayMessage           the message that should be shown if a client tries to connect to this port
     * @param numPlayers            the number that should be shown as numPlayers in the ping response
     * @param maxPlayers            the number that should be shown as maxPlayers in the ping response
     * @param reportedVersionNumber the string that should be shown as protocol version string in the ping response
     * @param motdMessage           the motd of this placeholder
     * @param protVersion           the number that should be shown as protocol version in the ping response
     */
    public PlaceHolder(int port, String awayMessage, int numPlayers, int maxPlayers, String reportedVersionNumber, String motdMessage, int protVersion) {
        this(port, awayMessage, numPlayers, maxPlayers);
        this.motdMessage = motdMessage;
        this.reportedVersionNumber = reportedVersionNumber;
        this.prot = protVersion;
    }

    /**
     * Starts the server sockets, accepts clients and handles them in a responder thread
     */
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            log.log(Level.SEVERE, "IOException while starting server socket!", e);
        }
        while (!serverSocket.isClosed()) {
            try {
                Socket s = serverSocket.accept();
                log.info("Received connection from " + s.getInetAddress().getHostAddress());
                new ResponderThread(reportedVersionNumber, prot, maxplayers, numplayers, motdMessage, s).start();
            } catch (SocketException ex) {
                if (!serverSocket.isClosed()) //if the socket is just closing, ignore this
                    log.log(Level.SEVERE, "SocketException while accepting client!", ex);
            } catch (IOException e) {
                log.log(Level.SEVERE, "IOException while accepting client!", e);
            }
        }
    }

    /**
     * Closes this placeholder
     */
    public void stopListening() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
