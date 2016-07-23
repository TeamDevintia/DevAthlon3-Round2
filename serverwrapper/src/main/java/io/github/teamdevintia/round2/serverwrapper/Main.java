package io.github.teamdevintia.round2.serverwrapper;

import io.github.teamdevintia.round2.serverwrapper.commands.CommandLineCommandHandler;
import io.github.teamdevintia.round2.serverwrapper.server.ServerWrapper;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import lombok.extern.java.Log;

import java.io.File;
import java.util.logging.*;

/**
 * Entry point
 *
 * @author MiniDigger
 */
@Log
public class Main {

    public static boolean running = true;

    /**
     * Entry point
     *
     * @param args the args. see documentation for more infos on supported parameters
     */
    public static void main(String[] args) {
        Logger rootlog = Logger.getLogger("");
        for (Handler h : rootlog.getHandlers()){
            h.setFormatter(new LogFormatter());
        }

        log.info("Loading ServerWrapper....");
        log.info("Parsing Options...");
        OptionParser parser = new OptionParser();
        //new startup options
        OptionSpec<String> rootFolderFlag = parser.accepts("root").withRequiredArg().defaultsTo(new File(".").getAbsolutePath());
        OptionSpec<Void> interactiveFlag = parser.accepts("interactive");

        // parse it
        OptionSet options = parser.parse(args);

        File root = new File(options.valueOf(rootFolderFlag));
        boolean interactive = options.has(interactiveFlag);

        log.info("Options parsed!");
        log.info("Starting wrapper...");

        ServerWrapper wrapper = new ServerWrapper();
        wrapper.init(root);

        log.info("Wrapper started!");

        if (interactive) {
            new CommandLineCommandHandler();
        } else {
            while (running) {

            }
        }
    }
}
