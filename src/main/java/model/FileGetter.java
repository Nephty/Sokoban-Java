package model;

import presenter.Main;
import view.AlertBox;

import java.io.*;
import java.util.ArrayList;

public class FileGetter {

    /**
     * Read an xsb file and return a arrayList of string made from it
     *
     * @param levelName The name of the xsb file
     * @param _def      The name of the folder where we wants to read the file
     * @return the arrayList of string made from the xsb file
     */
    public static ArrayList<String> loadFile(String levelName, String _def) {
        String directory = directory(_def);
        ArrayList<String> content = new ArrayList<>();
        try (
                FileReader fr = new FileReader(directory + levelName);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
            return content;
        } catch (FileNotFoundException e) {
            AlertBox.display("Minor error", "The file " + levelName + " isn't in " + directory);
            return null;

        }catch (IOException e) {
            AlertBox.display("Minor error", "An error occurred while reading the file :\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Save an arrayList of Strings in a file
     *
     * @param levelName The name of the file where we want to save
     * @param _def      The folder where we want to save it.
     * @param content   The arrayList of String we want to save
     */
    public static void saveFile(String levelName, String _def, ArrayList<String> content) {
        String directory = directory(_def);
        try (
                FileWriter fw = new FileWriter(directory+levelName);
                BufferedWriter bw = new BufferedWriter(fw)
                ){
            // Add the Strings to the file
            for (String line : content) {
                bw.write(line + "\n");
            }
        } catch (IOException e){
            AlertBox.display("Minor Error", "Could not save the file. Check the name and try again.");
        }
    }

    /**
     * Gives an array with all the file names in a directory
     *
     * @param _def The name of the directory where we want to make the list
     * @return an array the file names in the given directory
     */
    public static String[] levelList(String _def) {
        String s = Main.SEPARATOR;
        String directory = directory().concat("main"+s+"resources"+s+"level"+s+_def);
        java.io.File file = new java.io.File(directory);
        return file.list();
    }

    /**
     * @param _def The name of the directory where we want to count
     * @return The number of levels in the folder
     */
    public static int howManyLevel(String _def) {
        String s = Main.SEPARATOR;
        String dir = directory().concat("main"+s+"resources"+s+"level"+s+_def);
        System.out.println(dir);
        String[] list = levelList(_def);
        return list.length;
    }

    /**
     * @return The path to the src file
     */
    public static String directory() {
        String s = Main.SEPARATOR;
        return System.getProperty("user.dir").concat(s+"src"+s);
    }

    /**
     * @param _def      The folder where we want to load the file
     * @return The path to the folder
     */
    public static String directory(String _def) {
        String directory = directory();
        String s = Main.SEPARATOR;
        switch (_def) {
            case "moves":
                directory = directory.concat("main"+s+"resources"+s+"level"+s+"moves"+s);
                break;
            case "campaign":
                directory = directory.concat("main"+s+"resources"+s+"level"+s+"campaign"+s);
                break;
            case "test":
                directory = directory.concat("test"+s+"resources"+s);
                break;
            case "freePlay":
                directory = directory.concat("main"+s+"resources"+s+"level"+s+"freePlay"+s);
                break;
            case "saves":
                directory = directory.concat("main"+s+"resources"+s+"level"+s+"saves"+s);
                break;
            case "sound":
                directory = directory.concat("main"+s+"resources"+s+"sound"+s);
                break;
            case "json":
                directory = directory.concat("main"+s+"resources"+s+"json"+s);
                break;
            case "img":
                directory = directory.concat("main"+s+"resources"+s+"img"+s);
                break;
        }
        return directory;
    }
}