import Client.ClientManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {

    private static final int port = 7364;

    public static void main(String[] args) {
        while (true) {
            String serverName = readServerName();
            try {
                ClientManager.run(serverName, port);
            } catch (SocketException e) {
                System.err.println("The socket could not be opened");
                e.printStackTrace();
            } catch (UnknownHostException ignore) {
                // ignore
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }

    private static String readServerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server domain name: ");
        while (true) {
            String serverName = scanner.nextLine().trim();
            try {
                if (InetAddress.getAllByName(serverName).length > 0) {
                    return serverName;
                }
            } catch (UnknownHostException e) {
                System.err.print("Unknown host \"" + serverName + "\"\nGive valid server domain name: ");
            }
        }
    }

}
