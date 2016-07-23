package io.github.teamdevintia.round2.serverwrapper.exceptions;

import io.github.teamdevintia.round2.serverwrapper.server.ServerMod;
import io.github.teamdevintia.round2.serverwrapper.server.ServerVersion;

/**
 * Thrown when something tries to load a server jar which is not registered in the repo.
 *
 * @author MiniDigger
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
