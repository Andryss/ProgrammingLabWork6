package Server;

import Client.Request;
import Commands.Command;
import MovieObjects.FieldException;
import MovieObjects.JsonMovieCodec;
import Commands.CommandException;

import java.io.IOException;
import java.util.Queue;

/**
 * <p>ServerExecutor implements (2) step in ServerManager:</p>
 * <p>1) Get command queue from Request</p>
 * <p>2) Validate it in some times</p>
 * <p>3) Execute commands and make them build Response</p>
 */
public class ServerExecutor {
    /**
     * Name of environment variable with filename
     */
    private static final String envName = "MovieFile";
    private static ServerINFO serverINFO;

    static void initialize() throws FieldException, IOException {
        serverINFO = new ServerINFO();
        serverINFO.setCollectionFilename(System.getenv(envName));
        if (serverINFO.getCollectionFilename() == null) {
            throw new IOException("ERROR: environmental variable with name \"MovieFile\" doesn't exists");
        }
        serverINFO.setCollection(JsonMovieCodec.readFromFile(serverINFO.getCollectionFilename()));
    }

    private static void validateCommands(Queue<Command> commandQueue) throws CommandException {
        ServerINFO copiedServerINFO = serverINFO.clone();

        for (Command command : commandQueue) {
            try {
                command.execute(ExecuteState.VALIDATE, copiedServerINFO);
            } catch (CommandException e) {
                throw new CommandException(e.getCommand(), "Error in validation: " + e.getMessage());
            }
        }
    }

    private static void executeCommands(Queue<Command> commandQueue) throws CommandException {
        if (commandQueue.size() > 1) {
            validateCommands(commandQueue);
        }

        for (Command command : commandQueue) {
            command.execute(ExecuteState.EXECUTE, serverINFO);
        }
    }

    static void executeRequest(Request request) {
        ServerController.info("Request starts executing");

        ResponseBuilder.createNewResponse();
        try {
            ResponseBuilder.add("\u001B[34m" + "START: command \"" + request.getCommandName() + "\" start executing" + "\u001B[0m");
            executeCommands(request.getCommandQueue());
            ResponseBuilder.add("\u001B[32m" + "SUCCESS: command \"" + request.getCommandName() + "\" successfully completed" + "\u001B[0m");
        } catch (CommandException e) {
            ResponseBuilder.createNewResponse("\u001B[31m" + e.getMessage() + "\u001B[0m");
        }

        ServerController.info("Request executed");
    }

    static void saveCollection() {
        try {
            JsonMovieCodec.writeToFile(serverINFO.getCollectionFilename(),serverINFO.getCollection());
            ServerController.info("Collection saved");
        } catch (Throwable e) {
            ServerController.error("Can't save collection",e);
        }
    }

    /**
     * Enum with two main states of executing command
     */
    public enum ExecuteState {
        /**
         * When command do what it should do (with logging)
         */
        EXECUTE,
        /**
         * When command is validating, for example, in script (without logging)
         */
        VALIDATE
    }
}
