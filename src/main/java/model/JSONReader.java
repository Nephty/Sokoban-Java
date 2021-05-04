package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.AlertBox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JSONReader {
    JSONObject obj;

    /**
     * <Code>JSONReader</Code> Constructor.
     * It opens the json file and prepares it to read its content.
     * @param fileName The name of the JSON file
     */
    public JSONReader(String fileName) {
        try {
            JSONParser jsonparser = new JSONParser();
            FileReader reader = new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\json\\" + fileName);
            this.obj = (JSONObject) jsonparser.parse(reader);
        } //It is useless to throw a new ParseException because we can't set a custom text with that Exception.
        catch (IOException exception) {
            AlertBox.display("Fatal error", "A .json file could not be found. Check if no file is missing." +
                    "Check if the names have not been changed or if any file has not been deleted. " +
                    "You can run the FileIntegrity checker for further information. Missing file : " + fileName + ".");
            System.exit(-1);
        }
        catch (ParseException exception) {
            AlertBox.display("Fatal error", "A .json file could not be parsed. The content of the file " +
                    "could not be read properly. Please replace the following file : " + fileName + ".");
            System.exit(-1);
        }
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
