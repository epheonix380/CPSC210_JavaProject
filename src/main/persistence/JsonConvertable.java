package persistence;

import org.json.JSONObject;

public interface JsonConvertable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
