package io.github.teamdevintia.round2.serverwrapper.commands;

/**
 * A command for the interactive mode
 *
 * @author MiniDigger
 */
public interface Command {

    /**
     * Executes the command
     *
     * @param args the args, args[0] is the command name!
     */
    void execute(String[] args);
}
