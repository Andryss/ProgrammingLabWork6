package Client;

import Commands.HelpCommand;
import Server.Response;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class ClientConnector extends Connector {
    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static int serverPort;
    private static final ByteBuffer dataBuffer = ByteBuffer.allocate(12000);

    private ClientConnector() {}

    static void initialize(String serverName, int port) throws IOException, ClassNotFoundException {
        setConnection(serverName, port);
        checkConnection();
    }

    private static void setConnection(String serverName, int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        socket.setSoTimeout(5_000);
        serverAddress = InetAddress.getByName(serverName);
        serverPort = port;
    }

    private static void checkConnection() throws IOException, ClassNotFoundException {
        RequestBuilder.createNewRequest(new HelpCommand("help"));
        try {
            sendToServer(RequestBuilder.getRequest());
        } catch (SocketTimeoutException e) {
            throw new IOException("Server is not responding, try later or choose another server :(");
        }
    }

    static Response sendToServer(Request request) throws IOException, ClassNotFoundException {
        try {
            sendRequest(request);
        } catch (IOException e) {
            throw new IOException("Can't send request to server: " + e.getMessage(), e);
        }
        try {
            return acceptResponse();
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException("Can't receive response from server: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Can't find Response class in response from server: " + e.getMessage(), e);
        }
    }

    private static void sendRequest(Request request) throws IOException {
        sendPacket(objectToBuffer(request));
    }

    private static void sendPacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.send(packetWithData);
    }

    private static Response acceptResponse() throws IOException, ClassNotFoundException {
        receivePacket(dataBuffer.array());
        Response response = objectFromBuffer(dataBuffer.array());
        dataBuffer.clear();
        return response;
    }

    private static void receivePacket(byte[] buf) throws IOException {
        DatagramPacket packetWithData = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packetWithData);
        } catch (SocketTimeoutException e) {
            throw new SocketTimeoutException("Time to execute command run out. Server connection lost");
        }
    }
}
