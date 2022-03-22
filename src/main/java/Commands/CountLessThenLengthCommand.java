package Commands;

import MovieObjects.Movie;
import Server.ServerExecutor;
import Server.ServerINFO;

public class CountLessThenLengthCommand extends NameableCommand {

    private int length;

    public CountLessThenLengthCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (state == ServerExecutor.ExecuteState.VALIDATE) {
            return true;
        }
        println("Found " +
                server.getCollection().values().stream()
                        .filter(movie -> movie.getLength() < length)
                        .count()
                + " movies with length less than " + length);
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            length = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "value must be integer");
        }
    }
}
