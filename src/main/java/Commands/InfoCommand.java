package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class InfoCommand extends NameableCommand {

    public InfoCommand(String commandName) {
        super(commandName);
    }

    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException {
        if (state == ServerExecutor.ExecuteState.VALIDATE) {
            return true;
        }
        BasicFileAttributes attributes = null;
        try {
            File file = new File(server.getCollectionFilename());
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            println(e.getMessage());
        }
        println("Collection type: " + server.getCollection().getClass().getName());
        println("Initialization date: " + (attributes == null ? "unknown" : attributes.creationTime().toString()));
        println("Last modified: " + (attributes == null ? "unknown" : attributes.lastModifiedTime().toString()));
        println("Collection length: " + server.getCollection().size());
        return true;
    }

    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
