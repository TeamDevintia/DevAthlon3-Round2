package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.serverwrapper.FileUtil;
import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A minecraft server. Stores all infos about the server configuration
 *
 * @author MiniDigger
 */
@Log
@Getter
@AllArgsConstructor
public class Server {

    private int serverPort;
    private String name;
    private File serverFolder;
    private ServerVersion serverVersion;
    private ServerMod serverMod;
    private ServerJavaOps serverJavaOps;
    private ServerThread thread;
    private boolean running;

    /**
     * Starts the server (into a new thread)
     *
     * @throws ServerJarNotFoundException when a server is started with a unknown jar
     * @throws IOException                if something else goes wrong
     */
    public void start() throws ServerJarNotFoundException, IOException {
        ServerWrapper.getInstance().addServer(this);

        ServerJar jar = ServerWrapper.getInstance().getJarManager().getJar(serverMod, serverVersion);

        List<String> options = new ArrayList<>();
        options.add("java");
        options.addAll(serverJavaOps.getFlags());
        options.add("-Dcom.mojang.eula.agree=true"); // fuck you eula
        options.add("-Djline.terminal=jline.UnsupportedTerminal");
        options.add("-DserverName=" + name);
        options.add("-jar");
        options.add(ServerWrapper.getInstance().getJarManager().getFile(jar).getAbsolutePath());
        options.add("--port");
        options.add(serverPort + "");

        if (!serverFolder.exists()) {
            log.info("Server folder " + serverFolder.getAbsolutePath() + " does not exist, creating...");
            serverFolder.mkdirs();
        }

        File plugins = new File(serverFolder, "plugins");
        System.out.println(plugins.getAbsolutePath());
        if (!plugins.exists()) {
            System.out.println("mkdirs " + plugins.mkdirs());
        }

        if (serverMod == ServerMod.BUNGEE) {
            FileUtil.copyPlugin(FileUtil.BUNGEE, plugins);
        } else {
            FileUtil.copyPlugin(FileUtil.BUKKIT, plugins);
        }

        ProcessBuilder pb = new ProcessBuilder(options);
        pb.directory(serverFolder);

        running = true;
        thread = new ServerThread(this, pb, (server, statusCode) -> {
            log.info("Server " + server.getName() + " existed with status code " + statusCode);
            running = false;
        });
    }

    /**
     * @return the server object for the bungeecord server
     */
    public static Server getBungee() {
        return new ServerBuilder("bungee").javaOps(new ServerJavaOps(512, false)).version(ServerVersion.v1_10_R1).mod(ServerMod.BUNGEE).port(25565).build();
    }
}
