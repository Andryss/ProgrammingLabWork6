package Server;

import MovieObjects.FieldException;

import java.io.IOException;

public class ServerManager {

    public static void run(int port) throws IOException, FieldException {
        initializations(port);

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
        ServerConnector.bindChannel(port);
        ServerExecutor.initialize();
    }

}
