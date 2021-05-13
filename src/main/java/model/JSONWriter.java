package model;

import org.json.simple.JSONObject;
import view.AlertBox;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The <code>JSONWriter</code> is support for writing in json files. The methods and constructors make it very easy
 *  * to read object in the json file.
 */
public class JSONWriter {
    JSONObject obj;
    String fileName;
    JSONReader temporaryReader;
    /**
     * <Code>JSONWriter</Code> Constructor.
     * It opens the json file and prepares it to write inside it.
     * @param fileName The name of the JSON file
     */
    public JSONWriter(String fileName) {
        this.fileName = fileName;
        this.obj = new JSONObject();
        temporaryReader = new JSONReader(fileName);
    }

    /**
     * Add a new key and its value in the JSON file.
     * @param key The name of the key
     * @param value The value of the key we add.
     */
    public void add(String key, String value) {
        //noinspection unchecked
        temporaryReader.obj.put(key, value);

        try {
            FileWriter file = new FileWriter(FileGetter.directory("json")+ this.fileName);
            file.write(temporaryReader.obj.toString());
            file.flush();
        } catch (IOException exception) {
            AlertBox.display("Fatal error", "A .json file could not be found. Check if no file is missing.\n" +
                    "Check if the names have not been changed or if any file has not been deleted.\n" +
                    "You can run the FileIntegrity checker for further information.\nMissing file : " + fileName + ".");
            System.exit(-1);
        }
    }

    /**
     * Set the value of a key
     * @param key The name of the key
     * @param newValue The value we want to put in the file
     */
    public void set(String key, String newValue) {
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
            if (i == tempStr.length() - 2) {
                finalStr.append("\n");
            }
        }
        try {
            FileWriter file = new FileWriter(FileGetter.directory("json") + this.fileName);
            file.write(finalStr.toString());
            file.flush();
        } catch (IOException exception) {
            AlertBox.display("Fatal error", "A .json file could not be found. Check if no file is missing.\n" +
                    "Check if the names have not been changed or if any file has not been deleted.\n" +
                    "You can run the FileIntegrity checker for further information.\n Missing file : " + fileName + ".");
            System.exit(-1);
        }
    }
}
