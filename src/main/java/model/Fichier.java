package  model;

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
    public static ArrayList<String> loadFile(String levelName, String _def){
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
     * @throws IOException File not found.
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
     * Donne sous forme d'un tableau, les niveaux presents dans un fichier.
     * @param dir The directory where we want to make the list
     * @return Un tableau avec le nom des niveaux.
     */
    public static String[] levelList(String dir) {
        String directory = directory().concat(dir);
        File file = new File(directory);
        String[] content = file.list();
        return content;
    }

    /**
     * Donne le nombre de niveaux presents dans un fichier.
     * @param dir The directory where we want to count
     * @return Le nombre de niveaux dans un fichier
     */
    public static int howManyLevel(String dir){
        String[] list = levelList(dir);
        return list.length;
    }

    /**
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
        String directory = System.getProperty("user.dir").concat("\\src\\");
        return directory;
    }

    /**
     * Donne le chemin vers le dossier level.
     * @param levelName Le nom du fichier.
     * @param _def Si le fichier est un fichier "move" ou un fichier "save" ou juste un fichier "" (niveau).
     * @return Le chemin sous forme d'un String.
     */
    public static String directory(String levelName, String _def) {
        String directory = directory();
        if (_def.equals("campaign")){
            directory = directory.concat("main\\resources\\level\\campaign\\"+levelName);
        }else if (_def.equals("test")){
            directory = directory.concat("test\\resources\\"+levelName);
        }else if (_def.equals("freePlay")) {
            directory = directory.concat("main\\resources\\level\\freePlay\\"+levelName);
        } else{
            directory = directory.concat("main\\resources\\level\\campaign\\"+levelName);
        }
        return directory;
    }


    /**
     * Create an ArrayList of direction objects.
     * @param fileName (String) Name of the .mov file.
     * @param _def (String) The name of the directory (move,
     * @return ArrayList of direction objects
     * @throws IllegalArgumentException Throws an exception if the string is different of (R-D-U-L)
     */
    public static ArrayList<Direction> toDirectionList(String fileName, String _def) throws IllegalArgumentException{
        ArrayList<String> moves = loadFile(fileName, _def);
        ArrayList<Direction> res = new ArrayList<>();
        for (String line : moves) {
            for (int i = 0; i < line.length(); i++) {
                char move = line.charAt(i);
                switch (move) {
                    case 'R':
                        res.add(Direction.RIGHT);
                        break;
                    case 'U':
                        res.add(Direction.UP);
                        break;
                    case 'D':
                        res.add(Direction.DOWN);
                        break;
                    case 'L':
                        res.add(Direction.LEFT);
                        break;
                    case '\n':
                        break;
                }
            }
        }
        return res;
    }
}