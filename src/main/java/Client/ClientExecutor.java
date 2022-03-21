package Client;

import Commands.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClientExecutor {
    /**
     * Map with command, where key is a name of command and value is a class of command
     */
    private static final Map<String, Command> commandMap = new HashMap<>();
    /**
     * List with all successful executed commands
     */
    private static final List<String> history = new LinkedList<>();

    static void initialize() {
        fillCommandMap();
    }

    private static void fillCommandMap() {
        // TODO: add commands in command map
        commandMap.put("help", new HelpCommand("help"));
        //info
        //show
        //insert null {element}
        //update id {element}
        //remove_key null
        //clear
        //save --- FORBIDDEN!
        //execute_script file_name
        commandMap.put("exit", new ExitCommand("exit"));
        commandMap.put("history", new HistoryCommand("history", history));
        //replace_if_greater null {element}
        //remove_lower_key null
        //group_counting_by_length
        //count_less_than_length length
        //filter_by_mpaa_rating mpaaRating
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
        history.add(commandName);
        RequestBuilder.createNewRequest(commandName);
        command.buildRequest();
    }
}
