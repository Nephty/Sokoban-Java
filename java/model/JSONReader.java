package main.java.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    JSONObject obj;
    public JSONReader(String fileName) throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\resources\\json\\" + fileName);
        this.obj = (JSONObject) jsonparser.parse(reader);
    }

    public String getString (String key) {
        return this.obj.get(key).toString();
    }

    public int getInt(String key) {
        return Integer.parseInt(this.obj.get(key).toString());
    }

    public boolean getBoolean(String key) {
        return Boolean.getBoolean(this.obj.get(key).toString());
    }

    public byte getByte(String key) {
        return Byte.parseByte(this.obj.get(key).toString());
    }
}
