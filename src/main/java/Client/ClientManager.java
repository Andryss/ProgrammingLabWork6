package Client;

import Commands.CommandException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * <p>ClientManager consist of main client logic:</p>
 * <p>1) Read command from CMD</p>
 * <p>2) Validate command and build Request</p>
 * <p>3) Send Request to the server and get Response</p>
 * <p>4) Print Server response</p>
 */
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
