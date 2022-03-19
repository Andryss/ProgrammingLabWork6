package Commands;

import ReadersExecutors.CommandException;
import Server.ServerExecutor;
import Server.ServerINFO;

/**
 * interface Command represents all required command methods
 */
public interface Command {
    /**
     * Makes command executing
     * @param state tells method "to validate" or "to execute"
     * @return true if method complete without troubles
     * @see ServerExecutor.ExecuteState
     */
    boolean execute(ServerExecutor.ExecuteState state, ServerINFO server) throws CommandException;

    /**
     * Validate and set arguments for command
     * @param args String array with arguments
     * @throws BadArgumentsException if arguments are incorrect
     */
    void setArgs(String... args) throws BadArgumentsException;
}
