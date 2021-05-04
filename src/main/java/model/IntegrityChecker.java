package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

// TODO : move it out of the src and make imports working OR make it assume java file are there

public class IntegrityChecker {
    public static boolean checkFileIntegrity() {
        JSONReader jsonReader = new JSONReader("integrity.json");
        System.out.println("Initializing file integrity check up... 0%");
        System.out.println("Initializing file integrity check up... 100%");
        System.out.println();
        System.out.println();


        // SRC
        System.out.println("Checking path src\\... 0%");
        JSONObject src = jsonReader.getObject("src");
        File dir = new File("src");
        String[] listedDirs = dir.list();
        if (!contains(listedDirs, "main")) {
            System.out.println("A directory is missing : src\\main");
            return false;
        }
        System.out.println("Checking path src\\... 100%");
        System.out.println("Finished checking path src");
        System.out.println();


        // SRC\MAIN
        System.out.println("Checking path src\\main... 0%");
        JSONObject main = (JSONObject) src.get("main");
        dir = new File("src\\main");
        listedDirs = dir.list();
        if (!contains(listedDirs, "java")) {
            System.out.println("A directory is missing : src\\main\\java");
            return false;
        }
        System.out.println("Checking path src\\main... 50%");
        if (!contains(listedDirs, "resources")) {
            System.out.println("A directory is missing : src\\main\\resources");
            return false;
        }
        System.out.println("Checking path src\\main... 100%");
        System.out.println("Finished checking path src\\main");
        System.out.println();


        // SRC\MAIN\JAVA
        System.out.println("Checking path src\\main\\java... 0%");
        JSONObject java = (JSONObject) main.get("java");
        dir = new File("src\\main\\java");
        listedDirs = dir.list();
        if (!contains(listedDirs, "model")) {
            System.out.println("A directory is missing : src\\main\\java\\model");
            return false;
        }
        System.out.println("Checking path src\\main\\java... 33%");
        if (!contains(listedDirs, "presenter")) {
            System.out.println("A directory is missing : src\\main\\java\\presenter");
            return false;
        }
        System.out.println("Checking path src\\main\\java... 67%");
        if (!contains(listedDirs, "view")) {
            System.out.println("A directory is missing : src\\main\\java\\view");
            return false;
        }
        System.out.println("Checking path src\\main\\java... 100%");
        System.out.println("Finished checking path src\\main\\java");
        System.out.println();


        // SRC\MAIN\JAVA\MODEL
        System.out.println("Checking path src\\main\\java\\model... 0%");
        JSONObject model = (JSONObject) java.get("model");
        dir = new File("src\\main\\java\\model");
        listedDirs = dir.list();
        for (int i = 0; i < model.size(); i++) {
            if (i != 0 && i != model.size()-1) {
                System.out.println("Checking path src\\main\\java\\model... " + (int)((float)(100*i)/model.size()) + "%");
            }
            if (!contains(listedDirs, (String) model.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\java\\model\\" + model.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\java\\model... 100%");
        System.out.println("Finished checking path src\\main\\java\\model");
        System.out.println();


        // SRC\MAIN\JAVA\PRESENTER
        System.out.println("Checking path src\\main\\java\\presenter... 0%");
        JSONObject presenter = (JSONObject) java.get("presenter");
        dir = new File("src\\main\\java\\presenter");
        listedDirs = dir.list();
        for (int i = 0; i < presenter.size(); i++) {
            if (i != 0 && i != presenter.size()-1) {
                System.out.println("Checking path src\\main\\java\\presenter... " + (int)((float)(100*i)/presenter.size()) + "%");
            }
            if (!contains(listedDirs, (String) presenter.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\java\\presenter\\" + presenter.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\java\\presenter... 100%");
        System.out.println("Finished checking path src\\main\\java\\presenter");
        System.out.println();


        // SRC\MAIN\JAVA\VIEW
        System.out.println("Checking path src\\main\\java\\view... 0%");
        JSONObject view = (JSONObject) java.get("view");
        dir = new File("src\\main\\java\\view");
        listedDirs = dir.list();
        for (int i = 0; i < view.size(); i++) {
            if (i != 0 && i != view.size()-1) {
                System.out.println("Checking path src\\main\\java\\view... " + (int)((float)(100*i)/view.size()) + "%");
            }
            if (!contains(listedDirs, (String) view.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\java\\view\\" + view.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\java\\view... 100%");
        System.out.println("Finished checking path src\\main\\java\\view");
        System.out.println();

        System.out.println("Java files integrity checked. No java file missing.");
        System.out.println();

        // SRC\MAIN\RESOURCES
        System.out.println("Checking path src\\main\\resources... 0%");
        JSONObject resources = (JSONObject) main.get("resources");
        dir = new File("src\\main\\resources");
        listedDirs = dir.list();
        if (!contains(listedDirs, "img")) {
            System.out.println("A directory is missing : src\\main\\resources\\img");
            return false;
        }
        System.out.println("Checking path src\\main\\resources... 25%");
        if (!contains(listedDirs, "json")) {
            System.out.println("A directory is missing : src\\main\\resources\\json");
            return false;
        }
        System.out.println("Checking path src\\main\\resources... 50%");
        if (!contains(listedDirs, "level")) {
            System.out.println("A directory is missing : src\\main\\resources\\level");
            return false;
        }
        System.out.println("Checking path src\\main\\resources... 75%");
        if (!contains(listedDirs, "sound")) {
            System.out.println("A directory is missing : src\\main\\resources\\sound");
            return false;
        }
        System.out.println("Checking path src\\main\\resources... 100%");
        System.out.println("Finished checking path src\\main\\resources");
        System.out.println();


        // SRC\MAIN\RESOURCES\IMG
        System.out.println("Checking path src\\main\\resources\\img... 0%");
        JSONObject img = (JSONObject) resources.get("img");
        dir = new File("src\\main\\resources\\img");
        listedDirs = dir.list();
        if (!contains(listedDirs, "maps")) {
            System.out.println("A directory is missing : src\\main\\resources\\img\\maps");
            return false;
        }
        for (int i = 0; i < img.size()-1; i++) {
            if (i != 0 && i != img.size()-1) {
                System.out.println("Checking path src\\main\\resources\\img... " + (int)((float)(100*i)/img.size()) + "%");
            }
            if (!contains(listedDirs, (String) img.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\img\\" + img.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\img... 100%");
        System.out.println("Finished checking path src\\main\\resources\\img");
        System.out.println();


        // SRC\MAIN\RESOURCES\IMG\MAPS
        System.out.println("Checking path src\\main\\resources\\img\\maps... 0%");
        JSONObject maps = (JSONObject) img.get("maps");
        dir = new File("src\\main\\resources\\img\\maps");
        listedDirs = dir.list();
        for (int i = 0; i < maps.size(); i++) {
            if (i != 0 && i != maps.size()-1) {
                System.out.println("Checking path src\\main\\resources\\img\\maps... " + (int)((float)(100*i)/maps.size()) + "%");
            }
            if (!contains(listedDirs, (String) maps.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\img\\maps\\" + maps.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\img\\maps... 100%");
        System.out.println("Finished checking path src\\main\\resources\\img\\maps");
        System.out.println();


        // SRC\MAIN\RESOURCES\JSON
        System.out.println("Checking path src\\main\\resources\\json... 0%");
        JSONObject json = (JSONObject) resources.get("json");
        dir = new File("src\\main\\resources\\json");
        listedDirs = dir.list();
        for (int i = 0; i < json.size(); i++) {
            if (i != 0 && i != json.size()-1) {
                System.out.println("Checking path src\\main\\resources\\json... " + (int)((float)(100*i)/json.size()) + "%");
            }
            if (!contains(listedDirs, (String) json.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\json\\" + json.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\json... 100%");
        System.out.println("Finished checking path src\\main\\resources\\json");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL
        System.out.println("Checking path src\\main\\resources\\level... 0%");
        JSONObject level = (JSONObject) resources.get("level");
        dir = new File("src\\main\\resources\\level");
        listedDirs = dir.list();
        if (!contains(listedDirs, "campaign")) {
            System.out.println("A directory is missing : src\\main\\resources\\level\\campaign");
            return false;
        }
        System.out.println("Checking path src\\main\\level... 50%");
        if (!contains(listedDirs, "generator")) {
            System.out.println("A directory is missing : src\\main\\resources\\level\\generator");
            return false;
        }
        System.out.println("Checking path src\\main\\resources\\level... 100%");
        System.out.println("Finished checking path src\\main\\resources\\level");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL\CAMPAIGN
        System.out.println("Checking path src\\main\\resources\\level\\campaign... 0%");
        JSONObject campaign = (JSONObject) level.get("campaign");
        dir = new File("src\\main\\resources\\level\\campaign");
        listedDirs = dir.list();
        for (int i = 0; i < campaign.size(); i++) {
            if (i != 0 && i != campaign.size()-1) {
                System.out.println("Checking path src\\main\\resources\\level\\campaign... " + (int)((float)(100*i)/campaign.size()) + "%");
            }
            if (!contains(listedDirs, (String) campaign.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\level\\campaign\\" + campaign.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\level\\campaign... 100%");
        System.out.println("Finished checking path src\\main\\resources\\level\\campaign");
        System.out.println();


        // SRC\MAIN\RESOURCES\LEVEL\GENERATOR
        System.out.println("Checking path src\\main\\resources\\level\\generator... 0%");
        JSONObject generator = (JSONObject) level.get("generator");
        dir = new File("src\\main\\resources\\level\\generator");
        listedDirs = dir.list();
        for (int i = 0; i < generator.size(); i++) {
            if (i != 0 && i != generator.size()-1) {
                System.out.println("Checking path src\\main\\resources\\level\\generator... " + (int)((float)(100*i)/generator.size()) + "%");
            }
            if (!contains(listedDirs, (String) generator.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\level\\campaign\\" + generator.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\level\\generator... 100%");
        System.out.println("Finished checking path src\\main\\resources\\level\\generator");
        System.out.println();


        // SRC\MAIN\RESOURCES\SOUND
        System.out.println("Checking path src\\main\\resources\\sound... 0%");
        JSONObject sound = (JSONObject) resources.get("sound");
        dir = new File("src\\main\\resources\\sound");
        listedDirs = dir.list();
        for (int i = 0; i < sound.size(); i++) {
            if (i != 0 && i != sound.size()-1) {
                System.out.println("Checking path src\\main\\resources\\sound\\... " + (int)((float)(100*i)/sound.size()) + "%");
            }
            if (!contains(listedDirs, (String) sound.get(String.valueOf(i)))) {
                System.out.println("A file is missing : src\\main\\resources\\sound\\" + sound.get(String.valueOf(i)));
                return false;
            }
        }
        System.out.println("Checking path src\\main\\resources\\sound... 100%");
        System.out.println("Finished checking path src\\main\\resources\\sound");
        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("File integrity check finished. No missing file reported.");
        return true;
    }

    private static boolean contains(String[] lst, String has) {
        return Arrays.asList(lst).contains(has);
    }

}
