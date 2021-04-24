package model;

import java.io.*;
import java.util.ArrayList;

public class Fichier {

    /**
     * Read an xsb file and return a arrayList of string made from it
     *
     * @param levelName The name of the xsb file
     * @param _def      The name of the folder where we wants to read the file
     * @return the arrayList of string made from the xsb file
     */
    public static ArrayList<String> loadFile(String levelName, String _def) throws IOException{
        String directory = directory(levelName, _def);
        ArrayList<String> content = new ArrayList<>();
        try {
            File file = new File(directory);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
            fr.close();
            return content;
        } catch (Exception e) {
            throw new IllegalArgumentException("The file " + levelName + " isn't in " + directory);
        }
    }

    /**
     * Save an arrayList of Strings in a file
     *
     * @param levelName The name of the file where we want to save
     * @param _def      The folder where we want to save it.
     * @param content   The arrayList of String we want to save
     * @throws IOException Exception thrown when the given file could not be found
     */
    public static void saveFile(String levelName, String _def, ArrayList<String> content) throws IOException {
        String directory = directory(levelName, _def);
        try {
            File file = new File(directory);
            if (file.exists()) {
                // if the file already exists, we delete it.
                file.delete();
            }
            file.createNewFile();
            // Add the Strings to the file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String line : content) {
                bw.write(line + "\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e){
            throw new IllegalArgumentException("An error has occurred while saving the file");
        }
    }

    /**
     * Gives an array with all the file names in a directory
     *
     * @param dir The directory where we want to make the list
     * @return an array the file names in the given directory
     */
    public static String[] levelList(String dir) {
        String directory = directory().concat(dir);
        File file = new File(directory);
        String[] content = file.list();
        return content;
    }

    /**
     * @param dir The directory where we want to count
     * @return The number of levels in the folder
     */
    public static int howManyLevel(String dir) {
        String[] list = levelList(dir);
        return list.length;
    }

    /**
     * @return The path to the src file
     */
    public static String directory() {
        String directory = System.getProperty("user.dir").concat("\\src\\");
        return directory;
    }

    /**
     * @param levelName The name of a file.
     * @param _def      The folder where we want to load the file
     * @return The path to the folder
     */
    public static String directory(String levelName, String _def) {
        String directory = directory();
        if (_def.equals("moves")) {
            directory = directory.concat("main\\resources\\level\\moves\\" + levelName);
        } else if (_def.equals("campaign")) {
            directory = directory.concat("main\\resources\\level\\campaign\\" + levelName);
        } else if (_def.equals("test")) {
            directory = directory.concat("test\\resources\\" + levelName);
        } else if (_def.equals("freePlay")) {
            directory = directory.concat("main\\resources\\level\\freePlay\\" + levelName);
        } else {
            directory = directory.concat("main\\resources\\level\\campaign\\" + levelName);
        }
        return directory;
    }
}