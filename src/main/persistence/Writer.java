package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

// Represents writing string to json file
public class Writer {

    private String destination;
    private PrintWriter writer;

    // MODIFIES: This
    // EFFECTS: destination is where writer should write to
    public Writer(String destination) {
        this.destination = destination;
    }

    // EFFECTS: Writes jsonString to file
    public void write(String jsonString) throws IOException {
        open();
        saveToFile(jsonString);
        close();
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws IOException if error occurs during writing
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
