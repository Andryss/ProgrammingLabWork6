package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

import java.util.Scanner;

/**
 * Command, which updates an element with given id
 * @see NameableCommand
 */
public class UpdateCommand extends ElementCommand {

    public UpdateCommand(String commandName, Scanner reader) {
        this(commandName, reader, false);
    }

    public UpdateCommand(String commandName, Scanner reader, boolean readingFromFile) {
        super(commandName, reader, readingFromFile);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (!server.getCollection().containsKey(key)) {
            throw new CommandException(getCommandName(), "movie with key \"" + key + "\" doesn't exists");
        }
        server.getCollection().put(key, readMovie);
        if (state == ServerExecutor.ExecuteState.EXECUTE) {
            println("The movie has been updated");
        }
        return true;
    }
}
