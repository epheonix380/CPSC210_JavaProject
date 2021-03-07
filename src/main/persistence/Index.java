package persistence;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Represents the currently saved shops
public class Index {

    static final String source = "./data/index.json";
    private List<String> index;

    // MODIFIES: This
    // EFFECTS: IF an error occurs during reading:
    //              Throws IOException
    //          else:
    //              returns index
    public List<String> getIndex() throws IOException {
        readIndex();
        return this.index;
    }

    // MODIFIES: This
    // EFFECTS: IF an error occurs during reading:
    //              Throws IOException
    //          else:
    //              returns if index contains string
    public boolean contains(String string) throws IOException {
        readIndex();
        return index.contains(string);
    }

    // MODIFIES: This
    // EFFECTS: IF an error occurs during reading:
    //              Throws IOException
    //          else:
    //              adds string to index if it is not already in index and saves index to json file
    public void addToIndex(String string) throws IOException {
        readIndex();
        if (!index.contains(string)) {
            index.add(string);
            save();
        }
    }

    // EFFECTS: IF an error occurs during writing:
    //              Throws IOException
    //          else:
    //              converts index to JSONArray and saves to json file
    private void save() throws IOException {
        JSONArray json = new JSONArray();
        for (String str : index) {
            json.put(str);
        }
        Writer writer = new Writer(source);
        writer.write(json.toString());
    }

    // MODIFIES: This
    // EFFECTS: IF an error occurs during reading or writing:
    //              Throws IOException
    //          else IF string is in index:
    //              deletes string from index and saves
    //          else:
    //              Throw FileNotFoundException Exception
    public void delete(String string) throws IOException {
        readIndex();
        if (index.contains(string)) {
            String destination = "./data/shops/" + string + ".json";
            File file = new File(destination);
            file.delete();
            index.remove(string);
            save();
        } else {
            throw new FileNotFoundException();
        }
    }

    // MODIFIES: This
    // EFFECTS: IF an error occurs during reading:
    //              Throws IOException
    //          else:
    //              Reads index from json file and writes it to index in this
    private void readIndex() throws IOException {
        Reader reader = new Reader(source);
        String jsonData = reader.getJson();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(jsonData);
        } catch (JSONException e) {
            jsonArray = new JSONArray();
        }
        index = createIndex(jsonArray);
    }

    // EFFECTS: Converts JSONArray to List<String>
    private List<String> createIndex(JSONArray jsonArray) {
        List<String> index = new ArrayList<>();
        Iterator<Object> jsonIndex = jsonArray.iterator();
        int i = 0;

        for (Iterator<Object> it = jsonIndex; it.hasNext(); ) {
            String name = jsonArray.getString(i);
            index.add(name);
            i++;
            it.next();
        }
        return index;
    }
}
