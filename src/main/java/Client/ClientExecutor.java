package Client;

import Commands.Command;
import Commands.CommandException;
import Commands.HelpCommand;
import Commands.UndefinedCommandException;

import java.util.HashMap;
import java.util.Map;

public class ClientExecutor {
    /**
     * Map with command, where key is a name of command and value is a class of command
     */
    private static final Map<String, Command> commandMap = new HashMap<>();

    static void initialize() {
        fillCommandMap();
    }

    private static void fillCommandMap() {
        // TODO: add commands in command map
        commandMap.put("help", new HelpCommand("help"));
    }

    static void parseCommand(String inputLine) throws CommandException {
        String[] operands = inputLine.trim().split("\\s+", 2);
        if (operands.length == 0) {
            throw new UndefinedCommandException("");
        } else if (operands.length == 1) {
            executeCommand(operands[0], new String[0]);
        } else {
            String[] args = operands[1].split("\\s+");
            executeCommand(operands[0], args);
        }
    }

    private static void executeCommand(String commandName, String[] args) throws CommandException {
        Command command = commandMap.get(commandName);
        if (command == null) {
            throw new UndefinedCommandException(commandName);
        }
        command.setArgs(args);
        RequestBuilder.createNewRequest(commandName);
        command.buildRequest();
    }
}
