package Client;

import Commands.Command;
import Commands.NameableCommand;

public class RequestBuilder {

    private static Request request;

    private RequestBuilder() {}

    public static void createNewRequest(String commandName) {
        request = new Request(commandName);
    }

    public static void createNewRequest(NameableCommand command) {
        request = new Request(command.getCommandName());
        request.add(command);
    }

    public static void add(Command command) {
        request.add(command);
    }

    public static Request getRequest() {
        return request;
    }

}
