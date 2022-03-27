package Client;

import Commands.Command;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Request class contains all information client can send to the server in one class
 */
public class Request implements Serializable {

    private final String commandName;

    private final Queue<Command> commandQueue = new LinkedList<>();

    public Request(String commandName) {
        this.commandName = commandName;
    }

    void add(Command command) {
        commandQueue.add(command);
    }

    public String getCommandName() {
        return commandName;
    }

    public Queue<Command> getCommandQueue() {
        return commandQueue;
    }
}
