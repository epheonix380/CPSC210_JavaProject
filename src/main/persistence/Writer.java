package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

    private String destination;
    private PrintWriter writer;

    // EFFECTS: constructs writer to write to destination file
    public Writer(String destination) {
        this.destination = destination;
    }


    public void write(String jsonString) throws IOException {
        open();
        saveToFile(jsonString);
        close();
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    private void open() throws IOException {
        writer = new PrintWriter(new File(destination));
    }


    // MODIFIES: this
    // EFFECTS: closes writer
    private void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
