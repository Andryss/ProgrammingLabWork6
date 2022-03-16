package Client;

import Commands.Command;

import java.util.Queue;

public class Response {

    private Queue<Command> commandQueue;

    public Queue<Command> getCommandQueue() {
        return commandQueue;
    }
}
