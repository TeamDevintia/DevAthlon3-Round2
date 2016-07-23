package io.github.teamdevintia.round2.serverwrapper;

import io.github.teamdevintia.round2.serverwrapper.exceptions.ServerJarNotFoundException;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 23.07.2016.
 */
@Getter
public class Server {

    private String name;
    private File serverFolder;
    private ServerVersion serverVersion;
    private ServerMod serverMod;
    private ServerJavaOps serverJavaOps;
    private ServerThread thread;

    public void start() throws ServerJarNotFoundException, IOException {
        ServerJar jar = ServerWrapper.getInstance().getJarManager().getJar(serverMod, serverVersion);

        List<String> options = new ArrayList<>();
        options.add("java");
        options.add("-jar");
        options.add(jar.getFullFile().getAbsolutePath());
        options.addAll(serverJavaOps.getFlags());
        options.add("-DserverName=" + name);

        ProcessBuilder pb = new ProcessBuilder(options);
        pb.directory(serverFolder);

        thread = new ServerThread(this,pb);
    }
}
