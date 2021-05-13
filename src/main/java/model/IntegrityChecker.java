package model;

import org.json.simple.JSONObject;
import presenter.Main;

import java.io.File;
import java.util.Arrays;

/**
 * The <code>IntegrityChecker</code> is a class used to check if all the files of the game are in the good directories.
 * The name of the files and their position in the directories are stored in the JSON file "integrity.json".
 */
public class IntegrityChecker {

    /**
     * Check if all the files are in the good directories
     * @return True if the check is completed without error.
     */
    public static boolean checkFileIntegrity() {
        String s = Main.getFileDestination();
        JSONReader jsonReader = new JSONReader("integrity.json");
        System.out.println("Initializing file integrity check up... 0%");
        System.out.println("Initializing file integrity check up... 100%");
        System.out.println();
        System.out.println();


        // SRC
        System.out.println("Checking path src"+s+"... 0%");
        JSONObject src = jsonReader.getObject("src");
        File dir = new File("src");
        String[] listedDirs = dir.list();
        if (!contains(listedDirs, "main")) {
            System.out.println("A directory is missing : src"+s+"main");
            return false;
        }
        System.out.println("Checking path src"+s+"... 100%");
        System.out.println("Finished checking path src");
        System.out.println();


        // SRC\MAIN
        System.out.println("Checking path src"+s+"main... 0%");
        JSONObject main = (JSONObject) src.get("main");
        dir = new File("src"+s+"main");
        listedDirs = dir.list();
        if (!contains(listedDirs, "java")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"java");
            return false;
        }
        System.out.println("Checking path src"+s+"main... 50%");
        if (!contains(listedDirs, "resources")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources");
            return false;
        }
        System.out.println("Checking path src"+s+"main... 100%");
        System.out.println("Finished checking path src"+s+"main");
        System.out.println();


        // SRC\MAIN\JAVA
        System.out.println("Checking path src"+s+"main"+s+"java... 0%");
        JSONObject java = (JSONObject) main.get("java");
        dir = new File("src"+s+"main"+s+"java");
        listedDirs = dir.list();
        if (!contains(listedDirs, "model")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"java"+s+"model");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"java... 33%");
        if (!contains(listedDirs, "presenter")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"java"+s+"presenter");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"java... 67%");
        if (!contains(listedDirs, "view")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"java"+s+"view");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"java... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"java");
        System.out.println();


        // SRC\MAIN\JAVA\MODEL
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"model... 0%");
        JSONObject model = (JSONObject) java.get("model");
        dir = new File("src"+s+"main"+s+"java"+s+"model");
        listedDirs = dir.list();
        for (int i = 0; i < model.size(); i++) {
            if (i != 0 && i != model.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"java"+s+"model... " + (int)((float)(100*i)/model.size()) + "%");
            }
            if (!contains(listedDirs, (String) model.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"java"+s+"model"+s+"" + model.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"model... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"java"+s+"model");
        System.out.println();


        // SRC\MAIN\JAVA\PRESENTER
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"presenter... 0%");
        JSONObject presenter = (JSONObject) java.get("presenter");
        dir = new File("src"+s+"main"+s+"java"+s+"presenter");
        listedDirs = dir.list();
        for (int i = 0; i < presenter.size(); i++) {
            if (i != 0 && i != presenter.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"java"+s+"presenter... " + (int)((float)(100*i)/presenter.size()) + "%");
            }
            if (!contains(listedDirs, (String) presenter.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"java"+s+"presenter"+s+"" + presenter.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"presenter... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"java"+s+"presenter");
        System.out.println();


        // SRC\MAIN\JAVA\VIEW
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"view... 0%");
        JSONObject view = (JSONObject) java.get("view");
        dir = new File("src"+s+"main"+s+"java"+s+"view");
        listedDirs = dir.list();
        for (int i = 0; i < view.size(); i++) {
            if (i != 0 && i != view.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"java"+s+"view... " + (int)((float)(100*i)/view.size()) + "%");
            }
            if (!contains(listedDirs, (String) view.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"java"+s+"view"+s+"" + view.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"java"+s+"view... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"java"+s+"view");
        System.out.println();

        System.out.println("Java files integrity checked. No java file missing.");
        System.out.println();

        // SRC\MAIN\RESOURCES
        System.out.println("Checking path src"+s+"main"+s+"resources... 0%");
        JSONObject resources = (JSONObject) main.get("resources");
        dir = new File("src"+s+"main"+s+"resources");
        listedDirs = dir.list();
        if (!contains(listedDirs, "img")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"img");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"resources... 25%");
        if (!contains(listedDirs, "json")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"json");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"resources... 50%");
        if (!contains(listedDirs, "level")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"level");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"resources... 75%");
        if (!contains(listedDirs, "sound")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"sound");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"resources... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources");
        System.out.println();


        // SRC\MAIN\RESOURCES\IMG
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img... 0%");
        JSONObject img = (JSONObject) resources.get("img");
        dir = new File("src"+s+"main"+s+"resources"+s+"img");
        listedDirs = dir.list();
        if (!contains(listedDirs, "maps")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"img"+s+"maps");
            return false;
        }
        for (int i = 0; i < img.size()-1; i++) {
            if (i != 0 && i != img.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img... " + (int)((float)(100*i)/img.size()) + "%");
            }
            if (!contains(listedDirs, (String) img.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"img"+s+"" + img.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"img");
        System.out.println();


        // SRC\MAIN\RESOURCES\IMG\MAPS
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img"+s+"maps... 0%");
        JSONObject maps = (JSONObject) img.get("maps");
        dir = new File("src"+s+"main"+s+"resources"+s+"img"+s+"maps");
        listedDirs = dir.list();
        for (int i = 0; i < maps.size(); i++) {
            if (i != 0 && i != maps.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img"+s+"maps... " + (int)((float)(100*i)/maps.size()) + "%");
            }
            if (!contains(listedDirs, (String) maps.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"img"+s+"maps"+s+"" + maps.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"img"+s+"maps... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"img"+s+"maps");
        System.out.println();


        // SRC\MAIN\RESOURCES\JSON
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"json... 0%");
        JSONObject json = (JSONObject) resources.get("json");
        dir = new File("src"+s+"main"+s+"resources"+s+"json");
        listedDirs = dir.list();
        for (int i = 0; i < json.size(); i++) {
            if (i != 0 && i != json.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"json... " + (int)((float)(100*i)/json.size()) + "%");
            }
            if (!contains(listedDirs, (String) json.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"json"+s+"" + json.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"json... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"json");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level... 0%");
        JSONObject level = (JSONObject) resources.get("level");
        dir = new File("src"+s+"main"+s+"resources"+s+"level");
        listedDirs = dir.list();
        if (!contains(listedDirs, "campaign")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"level"+s+"campaign");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"level... 50%");
        if (!contains(listedDirs, "generator")) {
            System.out.println("A directory is missing : src"+s+"main"+s+"resources"+s+"level"+s+"generator");
            return false;
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"level");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL\CAMPAIGN
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"campaign... 0%");
        JSONObject campaign = (JSONObject) level.get("campaign");
        dir = new File("src"+s+"main"+s+"resources"+s+"level"+s+"campaign");
        listedDirs = dir.list();
        for (int i = 0; i < campaign.size(); i++) {
            if (i != 0 && i != campaign.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"campaign... " + (int)((float)(100*i)/campaign.size()) + "%");
            }
            if (!contains(listedDirs, (String) campaign.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"level"+s+"campaign"+s+"" + campaign.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"campaign... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"level"+s+"campaign");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL\GENERATOR
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"generator... 0%");
        JSONObject generator = (JSONObject) level.get("generator");
        dir = new File("src"+s+"main"+s+"resources"+s+"level"+s+"generator");
        listedDirs = dir.list();
        for (int i = 0; i < generator.size(); i++) {
            if (i != 0 && i != generator.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"generator... " + (int)((float)(100*i)/generator.size()) + "%");
            }
            if (!contains(listedDirs, (String) generator.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"level"+s+"campaign"+s+"" + generator.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"level"+s+"generator... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"level"+s+"generator");
        System.out.println();


        // SRC\MAIN\RESOURCES\SOUND
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"sound... 0%");
        JSONObject sound = (JSONObject) resources.get("sound");
        dir = new File("src"+s+"main"+s+"resources"+s+"sound");
        listedDirs = dir.list();
        for (int i = 0; i < sound.size(); i++) {
            if (i != 0 && i != sound.size()-1) {
                System.out.println("Checking path src"+s+"main"+s+"resources"+s+"sound"+s+"... " + (int)((float)(100*i)/sound.size()) + "%");
            }
            if (!contains(listedDirs, (String) sound.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src"+s+"main"+s+"resources"+s+"sound"+s+"" + sound.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src"+s+"main"+s+"resources"+s+"sound... 100%");
        System.out.println("Finished checking path src"+s+"main"+s+"resources"+s+"sound");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("File integrity check finished. No missing file reported.");
        return true;
    }

    /**
     * This method check if the String object is the lst.
     * @param lst The String table where we want to check
     * @param has The String we want to know if he's in the list
     * @return True if the String is in the list, false otherwise.
     */
    private static boolean contains(String[] lst, String has) {
        return Arrays.asList(lst).contains(has);
    }

}
