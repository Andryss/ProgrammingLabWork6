package Client;

import Commands.Command;

import java.util.LinkedList;
import java.util.Queue;

public class Request {

    private final Queue<Command> commandQueue = new LinkedList<>();

    void add(Command command) {
        commandQueue.add(command);
    }

    public Queue<Command> getCommandQueue() {
        return commandQueue;
    }
}
