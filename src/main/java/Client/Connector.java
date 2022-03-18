package Client;

import java.io.*;

public abstract class Connector {

    public static byte[] encodeSizeArray(int length) {
        assert length >= 0 : "Length must be at least 0";
        byte[] size = new byte[4];
        for (int i = 0; i < size.length; i++) {
            size[3-i] = (byte) (length >>> (i << 3));
        }
        return size;
    }

    public static <T> byte[] objectToBuffer(T obj) throws IOException {
        ByteArrayOutputStream byteStream;
        try (ObjectOutputStream stream = new ObjectOutputStream(byteStream = new ByteArrayOutputStream())){
            stream.writeObject(obj);
            stream.flush();
            return byteStream.toByteArray();
        }
    }

    public static int decodeSizeArray(byte[] size) {
        assert size.length == 4 : "Size length must equals 4";
        int length = 0;
        for (int i = 0; i < size.length; i++) {
            length |= size[3-i] << (i << 3);
        }
        return length;
    }

    public static <T> T objectFromBuffer(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return  (T) stream.readObject();
        }
    }
}
