package Server;

import MovieObjects.FieldException;

import java.io.IOException;
import java.net.InetAddress;

public class ServerManager {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ServerExecutor::saveCollection));
    }

    public static void run(int port) throws IOException, FieldException {
        ServerController.println("Initializations start");
        initializations(port);
        ServerController.println("Initializations completed");
        ServerController.println("Server started at: " + InetAddress.getLocalHost());

        while (true) {
            try {
                ServerExecutor.executeRequest(ServerConnector.receiveRequest());
                ServerConnector.sendToClient(ResponseBuilder.getResponse());
            } catch (ClassNotFoundException | IOException e) {
                // TODO: add logging of incorrect read class
                ServerController.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void initializations(int port) throws IOException, FieldException {
        ServerConnector.initialize(port);
        ServerExecutor.initialize();
    }

}
