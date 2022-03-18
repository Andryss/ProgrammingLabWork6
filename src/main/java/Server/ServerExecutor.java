package Server;

import Client.Request;
import Commands.Command;
import MovieObjects.FieldException;
import MovieObjects.JsonMovieCodec;
import MovieObjects.Movie;
import ReadersExecutors.CommandException;
import ReadersExecutors.Executor;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Queue;

public class ServerExecutor {
    /**
     * Name of environment variable with filename
     */
    private static final String envName = "MovieFile";
    private static ServerINFO serverINFO;

    public static void initialize() throws FieldException, IOException {
        serverINFO = new ServerINFO();
        serverINFO.setCollectionFilename(System.getenv(envName));
        if (serverINFO.getCollectionFilename() == null) {
            throw new NullPointerException("\u001B[31m" + "ERROR: environmental variable with name \"MovieFile\" doesn't exists" + "\u001B[0m");
        }
        serverINFO.setCollection(JsonMovieCodec.readFromFile(serverINFO.getCollectionFilename()));
    }

    private static void validateCommands(Queue<Command> commandQueue) throws CommandException {
        ServerINFO copiedServerINFO = new ServerINFO();
        copiedServerINFO.setCollection((Hashtable<Integer, Movie>) serverINFO.getCollection().clone());
        copiedServerINFO.setCollectionFilename(serverINFO.getCollectionFilename());

        for (Command command : commandQueue) {
            try {
                command.execute(Executor.ExecuteState.VALIDATE, copiedServerINFO);
            } catch (CommandException e) {
                throw new CommandException("Error in validation: " + e.getMessage());
            }
        }
    }

    private static void executeCommands(Queue<Command> commandQueue) throws CommandException {
        if (commandQueue.size() > 1) {
            validateCommands(commandQueue);
        }
        //executing
    }

    public static void executeRequest(Request request) throws CommandException {
        ResponseBuilder.createNewResponse();
        executeCommands(request.getCommandQueue());
    }

}
