package Client;


import Commands.CommandException;

import java.io.IOException;

public class ClientManager {
    public static void run(String serverName, int port) throws IOException, ClassNotFoundException {
        initializations(serverName, port);

        while (true) {
            try {
                ClientExecutor.parseCommand(ClientController.readLine());
                ClientController.print(ClientConnector.sendToServer(RequestBuilder.getRequest()).getMessage());
            } catch (IOException | ClassNotFoundException e) {
                // TODO: add error logging
                e.printStackTrace();
            } catch (CommandException e) {
                ClientController.printErr(e.getMessage());
            }
        }
    }

    private static void initializations(String serverName, int port) throws IOException, ClassNotFoundException {
        ClientConnector.initialize(serverName, port);
        ClientController.print("Connection with server was successful");
        ClientExecutor.initialize();
        ClientController.initialize();
    }
}
