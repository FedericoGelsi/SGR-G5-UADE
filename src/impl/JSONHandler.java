package impl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONHandler implements api.API_JSONHandler {
    @Override
    public Object readJson(String filename) throws Exception{
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    @Override
    public void writeJson(String filename, JSONObject data) throws Exception{

        try {
            FileWriter file = new FileWriter(filename);
            file.write(data.toJSONString());
            file.flush();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
}
