package io.github.teamdevintia.round2.serverwrapper;

/**
 * Created by Martin on 23.07.2016.
 */
public abstract class ServerThreadCallback {

    public abstract void call(Server server, int statusCode);
}
