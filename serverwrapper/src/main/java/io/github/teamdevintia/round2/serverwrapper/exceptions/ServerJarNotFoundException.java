package io.github.teamdevintia.round2.serverwrapper.exceptions;

import io.github.teamdevintia.round2.serverwrapper.server.ServerMod;
import io.github.teamdevintia.round2.serverwrapper.server.ServerVersion;

/**
 * Created by Martin on 23.07.2016.
 */
public class ServerJarNotFoundException extends Exception {

    private String message;

    public ServerJarNotFoundException(ServerMod serverMod, ServerVersion serverVersion) {
        message = "A ServerJar with mod " + serverMod + " and version " + serverVersion + " could not be found!";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
