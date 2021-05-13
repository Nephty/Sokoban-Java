package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.AlertBox;

import java.io.FileReader;
import java.io.IOException;

/**
 * The <Code>JSONReader</Code> is a support for reading .json files. The methods and constructors make it very easy
 * to read object in the json file.
 */
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
            FileReader reader = new FileReader(FileGetter.directory("json") + fileName);
            this.obj = (JSONObject) jsonparser.parse(reader);
        } //It is useless to throw a new ParseException because we can't set a custom text with that Exception.
        catch (IOException exception) {
            AlertBox.display("Fatal error", "A .json file could not be found. Check if no file is missing.\n" +
                    "Check if the names have not been changed or if any file has not been deleted.\n" +
                    "You can run the FileIntegrity checker for further information.\nMissing file : " + fileName + ".");
            System.exit(-1);
        }
        catch (ParseException exception) {
            AlertBox.display("Fatal error", "A .json file could not be parsed. The content of the file " +
                    "could not be read properly.\n Please replace the following file : " + fileName + ".");
            System.exit(-1);
        }
    }

    /**
     * @param key The name of the key we want to read the value
     * @return The Object value associated to the key
     */
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
