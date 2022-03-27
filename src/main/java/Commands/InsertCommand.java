package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

import java.util.Scanner;

/**
 * Command, which adds new element with given key
 * @see NameableCommand
 */
public class InsertCommand extends ElementCommand {

    public InsertCommand(String commandName, Scanner reader) {
        this(commandName, reader, false);
    }

    public InsertCommand(String commandName, Scanner reader, boolean readingFromFile) {
        super(commandName, reader, readingFromFile);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (server.getCollection().containsKey(key)) {
            throw new CommandException(getCommandName(), "movie with key \"" + key + "\" already exists");
        }
        server.getCollection().put(key, readMovie);
        if (state == ServerExecutor.ExecuteState.EXECUTE) {
            println("*put new element in the collection*");
        }
        return false;
    }
}
