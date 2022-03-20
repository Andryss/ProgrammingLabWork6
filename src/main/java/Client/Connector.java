package Client;

import java.io.*;

public abstract class Connector {

    public static <T> byte[] objectToBuffer(T obj) throws IOException {
        ByteArrayOutputStream byteStream;
        try (ObjectOutputStream stream = new ObjectOutputStream(byteStream = new ByteArrayOutputStream())){
            stream.writeObject(obj);
            stream.flush();
            return byteStream.toByteArray();
        }
    }

    public static <T> T objectFromBuffer(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return  (T) stream.readObject();
        }
    }
}
