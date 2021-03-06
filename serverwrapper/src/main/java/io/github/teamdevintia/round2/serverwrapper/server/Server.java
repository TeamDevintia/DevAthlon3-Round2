package io.github.teamdevintia.round2.serverwrapper.server;

import io.github.teamdevintia.round2.network.packet.DeletedServerPacket;
import io.github.teamdevintia.round2.network.packet.StartedServerPacket;
import io.github.teamdevintia.round2.network.packet.StoppedServerPacket;
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

        // start options
        List<String> options = new ArrayList<>();
        options.add("java");
        options.addAll(serverJavaOps.getFlags());
        options.add("-Dcom.mojang.eula.agree=true"); // fuck you eula
        options.add("-Djline.terminal=jline.UnsupportedTerminal"); // remove warning on windows
        options.add("-DserverName=" + name); // for use in the bukkit plugin
        options.add("-jar");
        options.add(ServerWrapper.getInstance().getJarManager().getFile(jar).getAbsolutePath());
        options.add("--port");
        options.add(serverPort + "");
        options.add("-o");
        options.add("false");

        // create folder
        if (!serverFolder.exists()) {
            log.info("Server folder " + serverFolder.getAbsolutePath() + " does not exist, creating...");
            serverFolder.mkdirs();
        }

        // copy over plugin
        File plugins = new File(serverFolder, "plugins");
        if (!plugins.exists()) {
            plugins.mkdirs();
        }

        if (serverMod == ServerMod.BUNGEE) {
            FileUtil.copyPlugin(FileUtil.BUNGEE, plugins);
        } else {
            FileUtil.copyPlugin(FileUtil.BUKKIT, plugins);
        }

        // copy over world and stuff (faster load time, persistent config)
        FileUtil.copyDir(FileUtil.SERVER, serverFolder);

        // set root dir
        ProcessBuilder pb = new ProcessBuilder(options);
        pb.directory(serverFolder);

        // autoattach
        if (ServerWrapper.getInstance().isAutoAttach()) {
            if (ServerWrapper.getInstance().getCommandHandler() != null) {
                ServerWrapper.getInstance().getCommandHandler().attach(name);
            }
        }

        // send packet
        StartedServerPacket packet = new StartedServerPacket(name, serverMod.name(), serverVersion.name(), serverPort);
        ServerWrapper.getInstance().sendPacket(packet);

        // start
        running = true;
        thread = new ServerThread(this, pb, (server, statusCode) -> {
            log.info("Server " + server.getName() + " existed with status code " + statusCode);
            running = false;

            // send packet
            StoppedServerPacket p = new StoppedServerPacket(name);
            ServerWrapper.getInstance().sendPacket(p);
        });
    }

    /**
     * Stops (if necessary) and deletes this server's folder from the disk
     *
     * @return if the action was successful
     */
    public boolean delete() {
        if (running) {
            if (getThread() != null) {
                getThread().stopServer();
            }
        }

        ServerWrapper.getInstance().removeServer(this);

        boolean b = FileUtil.deleteFileOrFolder(serverFolder.toPath());

        // send packet
        DeletedServerPacket packet = new DeletedServerPacket(name);
        ServerWrapper.getInstance().sendPacket(packet);

        return b;
    }

    /**
     * @return the server object for the bungeecord server
     */
    public static Server getBungee() {
        return new ServerBuilder("bungee").javaOps(new ServerJavaOps(512, false)).version(ServerVersion.v1_10_R1).mod(ServerMod.BUNGEE).port(25565).build();
    }
}
