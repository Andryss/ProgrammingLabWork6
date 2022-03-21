import Client.ClientController;
import Client.ClientManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientMain {

    private static final int port = 7364;

    public static void main(String[] args) {
        while (true) {
            String serverName = readServerName();
            try {
                ClientManager.run(serverName, port);
            } catch (SocketException e) {
                System.err.println("The socket could not be opened: " + e.getMessage());
            } catch (UnknownHostException ignore) {
                // ignore
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }

    private static String readServerName() {
        ClientController.print("Enter server domain name or IP (or \"exit\"): ");
        while (true) {
            String line = ClientController.readLine().trim();
            if (line.equals("exit")) {
                System.exit(0);
            }
            try {
                try {
                    byte[] address = parseAddress(line);
                    return InetAddress.getByAddress(address).getHostAddress();
                } catch (IOException ignore) {
                    //ignore
                }
                if (InetAddress.getAllByName(line).length > 0) {
                    return line;
                }
            } catch (UnknownHostException e) {
                ClientController.printlnErr("Unknown host \"" + line + "\"");
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
