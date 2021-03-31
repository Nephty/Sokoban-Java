package  main.java.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {
    JSONObject obj;
    String fileName;
    public JSONWriter(String fileName) throws IOException {
        this.fileName = fileName;
        this.obj = new JSONObject();
    }

    public void add(String key, String value) throws IOException, ParseException {
        JSONReader temporaryReader = new JSONReader(this.fileName);

        //noinspection unchecked
        temporaryReader.obj.put(key, value);

        try (FileWriter file = new FileWriter("resources\\json\\" + this.fileName)) {
            file.write(temporaryReader.obj.toString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key, String newValue) throws IOException, ParseException {
        JSONReader temporaryReader = new JSONReader(this.fileName);

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

        try (FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\resources\\json\\" + this.fileName)) {
            file.write(finalStr.toString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
