import Client.ClientController;
import Client.ClientManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class ClientMain {

    private static final int port = 52927;

    public static void main(String[] args) {
        while (true) {
            try {
                InetAddress serverAddress = readServerAddress();
                ClientController.println("Connecting to server \"" + serverAddress + "\"");
                try {
                    ClientManager.run(serverAddress, port);
                } catch (SocketException e) {
                    ClientController.println("The socket could not be opened: " + e.getMessage());
                    break;
                } catch (IOException | ClassNotFoundException e) {
                    ClientController.printlnErr(e.getMessage());
                    break;
                }
            } catch (NoSuchElementException e) {
                ClientController.printlnErr("Incorrect input (EOF). Try not to be so unpredictable!");
                break;
            }
        }
    }

    private static InetAddress readServerAddress() {
        ClientController.print("Enter server domain name or IP (or \"exit\"): ");
        while (true) {
            String line = ClientController.readLine().trim();
            if (line.equals("exit")) {
                System.exit(0);
            }
            try {
                try {
                    byte[] address = parseAddress(line);
                    return InetAddress.getByAddress(address);
                } catch (IOException ignore) {
                    //ignore
                }
                if (InetAddress.getAllByName(line).length > 0) {
                    return InetAddress.getByName(line);
                }
            } catch (UnknownHostException e) {
                ClientController.println("\u001B[31m" + "Unknown host \"" + line + "\"" + "\u001B[0m");
                ClientController.print("Enter VALID server domain name or IP: ");
            }
        }
    }

    private static byte[] parseAddress(String line) throws IOException {
        try {
            String[] operands = line.split("\\.");
            if (operands.length == 4) {
                byte[] address = new byte[4];
                for (int i = 0; i < operands.length; i++) {
                    address[i] = Byte.parseByte(operands[i]);
                }
                return address;
            }
            throw new Throwable();
        } catch (Throwable e) {
            throw new IOException();
        }
    }

}
