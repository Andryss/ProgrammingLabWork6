package Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileController {

    private Scanner reader;
    private File file;

    public FileController(File file) throws FileNotFoundException {
        this.file = file;
        this.reader = new Scanner(file);
    }

    public String readLine() {
        return reader.nextLine();
    }

    public boolean hasNextLine() {
        return reader.hasNextLine();
    }

    public Scanner getReader() {
        return reader;
    }

    public String getFileName() {
        return file.getName();
    }
}
