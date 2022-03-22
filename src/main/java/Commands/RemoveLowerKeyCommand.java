package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

public class RemoveLowerKeyCommand extends NameableCommand {
    private Integer key;

    public RemoveLowerKeyCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        server.getCollection().keySet().stream()
                .filter(key -> key < this.key)
                .forEach(key -> server.getCollection().remove(key));
        if (state == ServerExecutor.ExecuteState.EXECUTE) {
            println("All elements with key lower than \"" + key + "\" has been removed");
        }
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length != 1) {
            throw new BadArgumentsCountException(getCommandName(), 1);
        }
        try {
            this.key = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new BadArgumentsFormatException(getCommandName(), "integer");
        }
    }
}
