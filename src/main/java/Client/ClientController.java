package Client;

import java.io.PrintStream;
import java.util.Scanner;

public class ClientController {

    private static final Scanner reader = new Scanner(System.in);
    private static final PrintStream writer = System.out;
    private static final PrintStream errWriter = System.err;

    static void initialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("\u001B[36m" + "THANK YOU for choosing our app to work with collections.\n" +
                "Developers are searching for the best realizations. Have a nice day :)" + "\u001B[0m")));
        print("\u001B[36m" + "Hi! This is a simple client-server program for working with collection.");
        print("I'm waiting for your commands (type \"help\" for list of available commands)." + "\u001B[0m");
    }

    static String readLine() {
        return reader.nextLine();
    }

    static void print(String line) {
        writer.println(line);
    }

    static void printErr(String line) {
        errWriter.println(line);
    }
}
