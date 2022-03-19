package Client;

import Server.Response;

import java.io.*;
import java.net.*;

public class ClientConnector extends Connector {
    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static int serverPort;

    private ClientConnector() {}

    static void setConnection(String serverName, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName(serverName);
        serverPort = port;
    }

    static Response sendToServer(Request request) throws IOException, ClassNotFoundException {
        try {
            sendRequest(request);
        } catch (IOException e) {
            throw new IOException("Can't send request to server: " + e.getMessage(), e);
        }
        try {
            return acceptResponse();
        } catch (IOException e) {
            throw new IOException("Can't receive response from server: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Can't find Response class: " + e.getMessage(), e);
        }
    }

    private static void sendRequest(Request request) throws IOException {
        byte[] buf = objectToBuffer(request);
        byte[] size = encodeSizeArray(buf.length);
        sendPacket(size);
        sendPacket(buf);
    }

    private static void sendPacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.send(packetWithData);
    }

    private static Response acceptResponse() throws IOException, ClassNotFoundException {
        byte[] size = new byte[4];
        receivePacket(size);

        int length = decodeSizeArray(size);
        byte[] data = new byte[length];
        receivePacket(data);

        return objectFromBuffer(data);
    }

    private static void receivePacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.receive(packetWithData);
    }
}
