package Commands;

import Server.ResponseBuilder;
import Server.ServerExecutor;
import Server.ServerINFO;

/**
 * Command, which prints all elements in the collection
 * @see NameableCommand
 */
public class ShowCommand extends NameableCommand {

    public ShowCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) {
        if (state == ServerExecutor.ExecuteState.VALIDATE) {
            return true;
        }
        ResponseBuilder.add("Collection contains:");
        if (server.getCollection().size() == 0) {
            println("*nothing*");
        } else {
            server.getCollection().entrySet().stream().forEach(entry -> println(entry.getKey() + " - " + entry.getValue()));
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
