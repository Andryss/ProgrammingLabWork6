package Commands;

import Server.ServerExecutor;
import Server.ServerINFO;

/**
 * Command, which prints a list of available commands
 * @see NameableCommand
 * @see Command
 */
public class HelpCommand extends NameableCommand {

    public HelpCommand(String commandName) {
        super(commandName);
    }

    /**
     * @param state tells method "to validate" or "to execute"
     * @see ServerExecutor.ExecuteState
     * @see Command
     */
    @Override
    public boolean execute(ServerExecutor.ExecuteState state, ServerINFO serverINFO) {
        if (state == ServerExecutor.ExecuteState.VALIDATE) {
            return true;
        }
        print("*List of available commands*");
        print("help : print a list of available commands");
        print("info : print short info about the collection (type, init date, length etc)");
        print("show : print all elements in the collection");
        print("insert null {element} : add new element with given key");
        print("update null {element} : update an element with given key");
        print("remove_key null : delete an element with given key");
        print("clear : clear the collection");
        print("execute_script file_name : read and execute script from file");
        print("exit : end the program (without saving)");
        print("history : print last 13 commands (without arguments)");
        print("replace_if_greater null {element} : replace an element by key if the new value is greater than the old one");
        print("remove_lower_key null : remove all elements whose key is less than given");
        print("group_counting_by_length : group the elements by the value of the \"length\" field, print the number of elements in each group");
        print("count_less_than_length length : print the number of elements whose \"length\" less than the given");
        print("filter_by_mpaa_rating mpaaRating : print an elements whose \"mpaaRating\" is equal to the given");
        return true;
    }

    /**
     * @see Command
     * @see BadArgumentsException
     */
    @Override
    public void setArgs(String... args) throws BadArgumentsException {
        if (args.length > 0) {
            throw new BadArgumentsCountException(getCommandName());
        }
    }
}
