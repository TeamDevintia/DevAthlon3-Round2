package io.github.teamdevintia.round2.serverwrapper.server;

/**
 * Created by Martin on 23.07.2016.
 */
public interface ServerThreadCallback {

    void call(Server server, int statusCode);
}
