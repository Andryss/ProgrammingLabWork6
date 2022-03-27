package Server;

import Client.Request;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>ServerConnector implements (1) and (3) steps in ServerManager</p>
 * <p>There are some methods to send and receive datagrams</p>
 */
public class ServerConnector {
    private static DatagramChannel channel;
    private static Selector selector;
    private static SocketAddress client;
    private static final ByteBuffer dataBuffer = ByteBuffer.allocate(12000);

    private ServerConnector() {}

    static void initialize(int port) throws IOException {
        bindChannel(port);
    }

    private static void bindChannel(int port) throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        selector = Selector.open();
        channel.register(selector, SelectionKey.OP_READ);
        channel.bind(new InetSocketAddress(port));

    }

    static Request receiveRequest() throws IOException, ClassNotFoundException {
        ServerController.info("------------------------------- Ready for receiving -------------------------------");

        infinity:
        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (Iterator<SelectionKey> keyIterator = keys.iterator(); keyIterator.hasNext(); keyIterator.remove()) {
                SelectionKey key = keyIterator.next();
                if (key.isValid()) {
                    if (key.isReadable()) {
                        break infinity;
                    }
                }
            }
        }

        client = channel.receive(dataBuffer);
        Request request = ConnectorHelper.objectFromBuffer(dataBuffer.array());

        ServerController.info("Received " + dataBuffer.position() + " bytes buffer with queue " + request.getCommandQueue().toString());

        dataBuffer.clear();

        return request;
    }

    static void sendToClient(Response response) {
        ServerController.info("Sending to client " + client.toString() + " starts");

        try {
            dataBuffer.put(ConnectorHelper.objectToBuffer(response));
            dataBuffer.flip();
            channel.send(dataBuffer, client);
            dataBuffer.clear();
        } catch (IOException e) {
            ServerController.error(e.getMessage(), e);
        }

        ServerController.info("Sending to client completed");
    }
}
