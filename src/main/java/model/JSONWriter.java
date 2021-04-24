package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    JSONObject obj;
    String fileName;
    JSONReader temporaryReader;
    /**
     * <Code>JSONWriter</Code> Constructor.
     * It opens the json file and prepares it to write inside it.
     * @param fileName The name of the JSON file
     * @throws IOException Throws if the JSON file isn't found
     * @throws ParseException Exception thrown when the .json file could not be parsed
     */
    public JSONWriter(String fileName) throws IOException, ParseException {
        this.fileName = fileName;
        this.obj = new JSONObject();
        temporaryReader = new JSONReader(fileName);
    }

    /**
     * Add a new key and its value in the JSON file.
     * @param key The name of the key
     * @param value The value of the key we add.
     * @throws IOException Exception never thrown because if the json file isn't found, we can't create the JSONWriter
     * @throws ParseException Exception thrown when the .json file could not be parsed
     */
    public void add(String key, String value) throws IOException, ParseException {
        //noinspection unchecked
        temporaryReader.obj.put(key, value);

        FileWriter file = new FileWriter("resources\\json\\" + this.fileName);
        file.write(temporaryReader.obj.toString());
        file.flush();
    }

    /**
     * Set the value of a key
     * @param key The name of the key
     * @param newValue The value we want to put in the file
     * @throws IOException Exception never thrown because if the json file isn't found, we can't create the JSONWriter
     * @throws ParseException Exception thrown when the .json file could not be parsed
     */
    public void set(String key, String newValue) throws IOException, ParseException {
        temporaryReader.obj.remove(key);
        //noinspection unchecked
        temporaryReader.obj.put(key, newValue);

        String tempStr = temporaryReader.obj.toString();
        StringBuilder finalStr = new StringBuilder();
        for (int i = 0; i < tempStr.length(); i++) {
            finalStr.append(tempStr.charAt(i));
            switch (tempStr.charAt(i)) {
                case ',':
                case '{':
                    finalStr.append("\n");
                default:
            }
            if (i == tempStr.length()-2) {
                finalStr.append("\n");
            }
        }

        FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\resources\\json\\" + this.fileName);
        file.write(finalStr.toString());
        file.flush();

    }
}