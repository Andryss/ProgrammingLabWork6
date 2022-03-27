package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

/**
 * Command, which clears the collection
 * @see NameableCommand
 */
public class ClearCommand extends NameableCommand {

    public ClearCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        server.getCollection().clear();
        if (state == ServerExecutor.ExecuteState.EXECUTE) {
            println("Collection is clear now");
        }
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
