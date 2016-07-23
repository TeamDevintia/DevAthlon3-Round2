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
 * Created by Martin on 23.07.2016.
 */
@Log
public class Main {

    public static boolean running = true;

    public static void main(String[] args) {
        //TODO fix logging
        Logger global = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
        global.setLevel(Level.FINE);
        global.addHandler(new ConsoleHandler());
        for (Handler handler : global.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setFormatter(new LogFormatter());
            }
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
        }else{
            while (running){

            }
        }
    }
}
