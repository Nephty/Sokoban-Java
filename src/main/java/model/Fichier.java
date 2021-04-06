package main.java.model;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Fichier {

    /**
     * Lis le contenue d'un fichier.xsb et le retourn sous forme d'une ArrayList.
     * @param levelName Le nom du fichier a lire.
     * @param _def Si le fichier est un fichier "move" ou un fichier "save" ou juste un fichier "" (niveau).
     * @return Le contenue du fichier sous forme d'ArrayList.
     */
    public static ArrayList<String> loadFile(String levelName, String _def) throws IOException {
        // Donne le repertoire courant.
        String directory = directory(levelName, _def);
        // Instantie une ArrayList et la rempli du contenu du fichier.
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
        } 
        catch (Exception e) {
            throw new IllegalArgumentException("Le fichier " + levelName + " n'est pas present dans " + directory);
        }
    }

    /**
     * Sauvegarde un contenue dans un fichier.xsb.
     * @param levelName Le nom du fichier dans lequel en sauvegarde.
     * @param _def Si le fichier doit etre un fichier "move" ou un fichier "save" ou juste un fichier "" (niveau).
     * @param content Le contenu a sauvegarder dans le fichier.
     */
    public static void saveFile(String levelName, String _def, ArrayList<String> content) throws IOException {
        // Donne le repertoire courant.
        String directory = directory(levelName, _def);

        File file = new File(directory);
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
     * /!\ A finaliser /!\   isFile() & isDirectory()
     * Donne sous forme d'un tableau, les niveaux presents dans un fichier.
     * @return Un tableau avec le nom des niveaux.
     */
    public static String[] howManyLevel() {
        String directory = directory();      
        File file = new File(directory);
        String[] content = file.list();
        return content;
    }

    /**
     * /!\ A finaliser /!\   isFile() & isDirectory()
     * Donne sous forme d'un tableau, les niveaux presents dans un fichier.
     * @return Un tableau avec le nom des niveaux.
     */
    public static String[] howManySound() {
        String directory = directorySound();      
        File file = new File(directory);
        String[] content = file.list();
        return content;
    }

    /**
     * Donne le chemin vers le dossier level.
     * @return Le chemin sous forme d'un String.
     */
    public static String directorySound() {
        String directory = System.getProperty("user.dir").concat("\\src\\main\\resources\\sound");
        return directory;
    }

    /**
     * Donne le chemin vers le dossier level.
     * @return Le chemin sous forme d'un String.
     */
    public static String directory() {
        String directory = System.getProperty("user.dir").concat("\\src\\main\\resources\\level");
        return directory;
    }

    /**
     * Donne le chemin vers le dossier level.
     * @param levelName Le nom du fichier.
     * @param _def Si le fichier est un fichier "move" ou un fichier "save" ou juste un fichier "" (niveau).
     * @return Le chemin sous forme d'un String.
     */
    public static String directory(String levelName, String _def) {
        String directory = System.getProperty("user.dir").concat("\\src\\resources\\level");
        // Acces au fichier "move" ou "save" ou "level".
        if(_def.equals("move")) {
            directory = directory().concat("\\move\\"+levelName);
        }
        else if(_def.equals("save")) {
            directory = directory().concat("\\save\\"+levelName);
        }
        else {
            directory = directory().concat("\\"+levelName);
        }
        return directory;
    }
}