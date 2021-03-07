package persistence;

import org.json.JSONObject;

// Represents a Class that can be converted to JSON
public interface JsonConvertable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
