package Client;

import Commands.CommandException;

import java.io.File;
import java.io.FileNotFoundException;

public class FileManager {

    private final FileController controller;
    private final FileExecutor executor;

    public FileManager(File file, FileExecutor caller) throws FileNotFoundException {
        this.controller = new FileController(file);
        this.executor = new FileExecutor(this.controller, caller);
    }

    public void buildRequest() throws CommandException {
        while (controller.hasNextLine()) {
            try {
                executor.parseCommand(controller.readLine());
            } catch (CommandException e) {
                throw new CommandException(e.getCommand(), "file \"" + controller.getFileName() + "\" "
                        + "command " + executor.getCommandNumber() + " \"" + e.getCommand() + "\" - " + e.getMessage());
            }
        }
    }
}
