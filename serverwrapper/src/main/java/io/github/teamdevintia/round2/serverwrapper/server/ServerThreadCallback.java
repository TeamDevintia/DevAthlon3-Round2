package io.github.teamdevintia.round2.serverwrapper.server;

/**
 * A callback that gets executed when a server thread (and the underlying server) stops
 */
public interface ServerThreadCallback {

    /**
     * gets called when the server thread (and the underlying server) stops
     *
     * @param server the server that just stopped
     * @param statusCode the status code the process returned
     */
    void call(Server server, int statusCode);
}
