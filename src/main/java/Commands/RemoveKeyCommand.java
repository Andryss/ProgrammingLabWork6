package Commands;

import Server.ResponseBuilder;
import Server.ServerExecutor;
import Server.ServerINFO;

public class RemoveKeyCommand extends NameableCommand {
    private Integer key;

    public RemoveKeyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (server.getCollection().remove(key) != null) {
            if (state == ServerExecutor.ExecuteState.EXECUTE) {
                println("Element with key \"" + key + "\" has been removed");
            }
        } else {
            if (state == ServerExecutor.ExecuteState.EXECUTE) {
                println("Nothing has been removed");
            }
        }
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }
}
