package io.github.teamdevintia.round2.serverwrapper.placeholder;

/**
 * A builder to create placeholders
 *
 * @author MiniDigger
 */
public class PlaceHolderBuilder {

    private String reportedVersionNumber, awayMessage, motdMessage;
    private int numPlayers, maxPlayers, protocol = 47, port = -1;

    /**
     * @param numPlayers the number that should be shown for numPlayers in the ping response
     */
    public PlaceHolderBuilder numPlayer(int numPlayers) {
        this.numPlayers = numPlayers;
        return this;
    }

    /**
     * @param maxPlayers the number that should be shown for maxPlayers in the ping response
     * @return
     */
    public PlaceHolderBuilder maxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    /**
     * @param protocol the protocol version number that should be shown for maxPlayers in the ping response
     * @return
     */
    public PlaceHolderBuilder protocol(int protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * @param port the port this placeholder should listen too
     * @return
     */
    public PlaceHolderBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * @param version the version string that should be shown for version in the ping response
     * @return
     */
    public PlaceHolderBuilder version(String version) {
        this.reportedVersionNumber = version;
        return this;
    }

    /**
     * @param awayMessage the message that should be shown as kick reason when the player tries to join
     * @return
     */
    public PlaceHolderBuilder awayMessage(String awayMessage) {
        this.awayMessage = awayMessage;
        return this;
    }

    /**
     * @param motd the motd message for this placeholder
     * @return
     */
    public PlaceHolderBuilder motd(String motd) {
        this.motdMessage = motd;
        return this;
    }

    /**
     * @return builds the placeholder and starts it
     */
    public PlaceHolder build() {
        if (port == -1) throw new IllegalStateException("Port must be set");
        if (reportedVersionNumber == null) throw new IllegalStateException("version must be set");
        if (awayMessage == null) throw new IllegalStateException("AwayMessage must be set");
        if (motdMessage == null) throw new IllegalStateException("MotD must be set");

        return new PlaceHolder(port, awayMessage, numPlayers, maxPlayers, reportedVersionNumber, motdMessage,protocol);
    }
}
