package Commands;

import MovieObjects.Movie;
import Server.ServerExecutor;
import Server.ServerINFO;

import java.util.Scanner;

public class ReplaceIfGreaterCommand extends ElementCommand {

    public ReplaceIfGreaterCommand(String commandName, Scanner reader) {
        this(commandName, reader, false);
    }

    public ReplaceIfGreaterCommand(String commandName, Scanner reader, boolean readingFromFile) {
        super(commandName, reader, readingFromFile);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (!server.getCollection().containsKey(key)) {
            throw new CommandException(getCommandName(), "movie with key \"" + key + "\" doesn't exists");
        }
        if (readMovie.compareTo(server.getCollection().get(key)) > 0) {
            server.getCollection().put(key, readMovie);
            if (state == ServerExecutor.ExecuteState.EXECUTE) {
                println("Element greater than the old one has been inserted");
            }
        } else {
            if (state == ServerExecutor.ExecuteState.EXECUTE) {
                println("Nothing was happened");
            }
        }
        return true;
    }
}
