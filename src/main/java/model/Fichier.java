package main.java.model;

import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;

public class Fichier {

    /**
     * Sauvegarde le niveau en cours.
     * @param levelName Le nom du fichier dans le quel on sauvegarde.
     * @param content Le contenu a sauvegarder.
     */
    public static void saveFile(String levelName, ArrayList<String> content) throws IOException {      
        String levelPath = getlevelPath(_saveAdd(levelName), "level/save");

        File file = new File(levelPath);

        if (file.exists()) {
            // si le fichier existe, alors on le supprime.
            file.delete();
        }

        // cree le fichier
        file.createNewFile();
            
        // Ajouter le contenu au fichier
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line : content) {
            bw.write(line +"\n");
        }
        bw.close();
        fw.close();
    }

    /**
     * Charge le niveau et le retourne.
     * @param levelName Nom du fichier a charger.
     * @param save Si Vrai, va rechercher le niveau dans le sous-dossier "save" de "level". Si faux dans le dossier "level".
     * @return Le niveau sous forme d'une arraylist.
     */
    public static String loadFile(String levelName, boolean save) throws IOException{
        String levelPath;
        String fileName;
        if (save) {
            fileName = "level/save";
            levelName = _saveAdd(levelName);
            levelPath = getlevelPath(levelName, fileName);
        } else {
            fileName = "level";
            levelPath = getlevelPath(levelName, fileName);
        }
        
        ArrayList<String> content = new ArrayList<>();
        if (areYouHere(levelName, fileName)) {
            File file = new File(levelPath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
            fr.close();
            String res = "";
            for (String el : content){
                res += el;
            }
            return res;
        } else {
            throw new IllegalArgumentException("Le fichier " + levelName + " n'est pas present");
        }
    }

    /**
     * Methode pour modifier un fichier deja existant. <br>
     * /!\ PAS ENCORE FAIT /!\ <br>
     * A voir si je fait ainsi ?
     */
    public static void editFile(String levelName, ArrayList<String> content) {
        System.out.println("pass");
    }

    /**
     * Ajoute "_save" au nom du level avant le ".xsb".
     * @param levelName
     * @return
     */
    public static String _saveAdd(String levelName) {
        // Ajoute "_save" au nom.
        String levelNameSave = (levelName.substring(0, levelName.length()-4)) + "_save.xsb";
        return levelNameSave;
    }

    /**
     * Chemin relatif
     * @return 
     */
    public static Path whereAmI() {
        Path path = Paths.get("../", "ressources");     
        return path.toAbsolutePath();
    }

    public static String getFilePath(String fileName) {
        String filePath = whereAmI().resolve(fileName).toString();
        return filePath;
    }

    public static String getlevelPath(String levelName, String fileName) {   
        String levelPath = getFilePath(fileName).concat("/".concat(levelName));
        return levelPath;
    }

    /**
     * Donne le nom des niveaux present dans le fichier.
     * @return une ArrayList<String> de nom des niveaux.
     */
    public static String[] howManyLevel(String filePath) {
        File file = new File(filePath);
        String[] content = file.list();
        return content;
    }

    /**
     * Dit si un fichier est present dans le dossier.
     * @param levelName Nom du fichier rechercher.
     * @param fileName Nom du dossier dans le quel on cherche.
     * @return
     */
    public static boolean areYouHere(String levelName, String fileName) {
        String[] listName = howManyLevel(getFilePath(fileName));
        for(String name : listName) {
            if(name.equals(levelName)){
                return true;
            }
        }
        return false;
    }

}