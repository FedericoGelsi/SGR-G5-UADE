package api;

import org.json.simple.JSONObject;

public interface API_JSONHandler {
    Object readJson(String filename) throws Exception;

    void writeJson(String filename, JSONObject data) throws Exception;
}
