package Server;

import MovieObjects.FieldException;

import java.io.IOException;

public class ServerManager {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(ServerExecutor::saveCollection));
    }

    public static void run(int port) throws IOException, FieldException {
        System.out.println("Initializations start");
        initializations(port);
        System.out.println("Initializations completed");

        while (true) {
            try {
                ServerExecutor.executeRequest(ServerConnector.receiveRequest());
                ServerConnector.sendToClient(ResponseBuilder.getResponse());
            } catch (ClassNotFoundException | IOException e) {
                // TODO: add logging of incorrect read class
                e.printStackTrace();
            }
        }
    }

    private static void initializations(int port) throws IOException, FieldException {
        ServerConnector.initialize(port);
        ServerExecutor.initialize();
    }

}
