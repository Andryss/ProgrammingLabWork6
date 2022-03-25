package Client;


import Commands.CommandException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

public class ClientManager {
    public static void run(InetAddress serverAddress, int port) throws IOException, ClassNotFoundException {
        initializations(serverAddress, port);

        while (true) {
            try {
                ClientExecutor.parseCommand(ClientController.readLine());
                ClientController.println(ClientConnector.sendToServer(RequestBuilder.getRequest()).getMessage());
            } catch (SocketTimeoutException e) {
                ClientController.printlnErr("Server isn't responding (try again later or choose another server)");
            } catch (IOException | ClassNotFoundException | CommandException e) {
                ClientController.printlnErr(e.getMessage());
            } catch (NoSuchElementException e) {
                ClientController.printlnErr("Invalid input \"EOF\"");
                break;
            }
        }
    }

    private static void initializations(InetAddress serverAddress, int port) throws IOException, ClassNotFoundException {
        ClientConnector.initialize(serverAddress, port);
        ClientController.println("Connection to server was successful");
        ClientExecutor.initialize();
        ClientController.initialize();
    }
}
