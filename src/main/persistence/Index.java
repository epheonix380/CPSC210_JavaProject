package persistence;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Index {

    static final String source = "./data/index.json";
    private List<String> index;

    public Index() {
    }

    public List<String> getIndex() throws IOException {
        readIndex();
        return this.index;
    }

    public boolean contains(String string) throws IOException {
        readIndex();
        return index.contains(string);
    }

    public void addToIndex(String string) throws IOException {
        readIndex();
        if (!index.contains(string)) {
            index.add(string);
            save();
        }
    }

    private void save() throws IOException {
        JSONArray json = new JSONArray();
        for (String str : index) {
            json.put(str);
        }
        Writer writer = new Writer(source);
        writer.write(json.toString());
    }

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
