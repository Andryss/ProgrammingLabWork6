package Client;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientController {

    private static final Scanner reader = new Scanner(System.in);
    private static final PrintStream writer = System.out;
    private static final PrintStream errWriter = System.err;

    private ClientController() {}

    static void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("\u001B[36m" + "THANK YOU for choosing our app to work with collections.\n" +
                "Developers are searching for the best realizations. Have a nice day :)" + "\u001B[0m")));
        println("\u001B[36m" + "Hi! This is a simple client-server program for working with collection.");
        println("I'm waiting for your commands (type \"help\" for list of available commands)." + "\u001B[0m");
    }

    public static String readLine() {
        return reader.nextLine();
    }

    public static void println(String line) {
        writer.println(line);
    }

    public static void print(String line) {
        writer.print(line);
    }

    public static void printlnErr(String line) {
        errWriter.println(line);
    }

    public static Scanner getReader() {
        return reader;
    }
}
