package Server;

import Client.Connector;
import Client.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerConnector extends Connector {
    private static DatagramChannel channel;
    private static SocketAddress client;

    private ServerConnector() {}

    public static void run(int port) throws IOException, ClassNotFoundException {
        bindChannel(port);
        while (true) {
            ServerExecutor.executeRequest(receiveRequest());
        }
    }

    private static void bindChannel(int port) throws IOException {
        channel = DatagramChannel.open();
        channel.bind(new InetSocketAddress(port));
    }

    private static Request receiveRequest() throws IOException, ClassNotFoundException {
        ByteBuffer size = ByteBuffer.allocate(4);
        client = channel.receive(size);

        int length = decodeSizeArray(size.array());
        ByteBuffer data = ByteBuffer.allocate(length);
        assert client == channel.receive(data) : "Received data has another address";

        return objectFromBuffer(data.array());
    }

    void sendToClient(Response response) throws IOException {
        ByteBuffer data = ByteBuffer.wrap(objectToBuffer(response));
        ByteBuffer size = ByteBuffer.wrap(encodeSizeArray(data.capacity()));
        channel.send(size, client);
        channel.send(data, client);
    }
}
