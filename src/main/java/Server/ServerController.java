package Server;

import java.io.PrintStream;

public class ServerController {

    private static final PrintStream writer = System.out;

    public static void println(String line) {
        writer.println(line);
    }

}
