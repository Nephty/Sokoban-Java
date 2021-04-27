package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    JSONObject obj;

    /**
     * <Code>JSONReader</Code> Constructor.
     * It opens the json file and prepares it to read its content.
     * @param fileName The name of the JSON file
     * @throws IOException Thrown if the JSON file isn't found
     * @throws ParseException Thrown if there's an error in the content of the JSON file
     */
    public JSONReader(String fileName) throws IOException, ParseException {
        try {
            JSONParser jsonparser = new JSONParser();
            FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\json\\" + fileName);
            this.obj = (JSONObject) jsonparser.parse(reader);
        } catch (IOException e1) {
            throw new IOException(fileName + "isn't in the json folder");
        } //It is useless to throw a new ParseException because we can't set a custom text with that Exception.
    }

    public JSONObject getObject(String key) {
        return (JSONObject) this.obj.get(key);
    }

    /**
     * @param key The name of the key we want to read the value
     * @return The string value associated to the key
     */
    public String getString (String key) {
        return this.obj.get(key).toString();
    }

    /**
     * @param key The name of the key we want to read the value
     * @return The Int value associated to the key
     */
    public int getInt(String key) {
        return Integer.parseInt(this.obj.get(key).toString());
    }

    /**
     * @param key The name of the key we want to read the value
     * @return The Boolean value associated to the key
     */
    public boolean getBoolean(String key) {
        return Boolean.getBoolean(this.obj.get(key).toString());
    }

    /**
     * @param key The name of the key we want to read the value
     * @return The Byte value associated to the key
     */
    public byte getByte(String key) {
        return Byte.parseByte(this.obj.get(key).toString());
    }


}
