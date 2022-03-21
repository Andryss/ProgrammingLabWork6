import MovieObjects.FieldException;
import Server.ServerManager;

import java.io.IOException;

public class ServerMain {

    private static final int port = 7364;

    public static void main(String[] args) {
        try {
            ServerManager.run(port);
        } catch (FieldException e) {
            System.err.println("Problems with Movie File: " + e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            //e.printStackTrace();
        }
    }

}
