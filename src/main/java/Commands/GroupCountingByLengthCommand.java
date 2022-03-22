package Commands;

import MovieObjects.Movie;
import Server.ResponseBuilder;
import Server.ServerExecutor;
import Server.ServerINFO;

import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupCountingByLengthCommand extends NameableCommand {

    public GroupCountingByLengthCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (state == ServerExecutor.ExecuteState.VALIDATE) {
            return true;
        }
        ResponseBuilder.add("*groups by length*");
        server.getCollection().values().stream()
                .map(Movie::getLength)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((length, count) -> ResponseBuilder.add(count + " movies with length " + length));
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName(), 0);
        }
    }
}
