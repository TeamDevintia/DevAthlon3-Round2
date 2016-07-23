package io.github.teamdevintia.round2.serverwrapper;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 23.07.2016.
 */
@Log
@Getter
public class ServerWrapper {

    private static ServerWrapper INSTANCE;

    private List<Server> servers;
    private ServerJarManager jarManager;
    private File root;

    public ServerWrapper() {
        INSTANCE = this;
    }

    public void init(File root) {
        this.root = root;
        servers = new ArrayList<>();

        // load jars
        jarManager = new ServerJarManager(new File(root, "repo"));
        jarManager.init();
        long loaded = jarManager.checkAvailability();
        log.info("Found " + loaded + " ServerJars in repo " + jarManager.getRepo());
    }


    public static ServerWrapper getInstance() {
        return INSTANCE;
    }
}
