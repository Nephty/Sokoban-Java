package model;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Generate {

    private Random random = new Random();

    private int line;
    private int row;
    private int numberOfObject;
    private boolean[][] tabPosVal;
    public ArrayList<String> content = new ArrayList<>();

    public Generate(int line, int row) {
        // /!\ LA TAILLE NE PEUT ÊTRE PLUS GRANDE QUE LE NOMBRE "20" /!\
        this.line = line;
        this.row = row;
        createMap();
        contentToLevel();
    }

    public Generate(int line) {
        // /!\ LA TAILLE NE PEUT ÊTRE PLUS GRANDE QUE LE NOMBRE "20" /!\
        this.line = line;
        this.row = line;
        createMap();
        contentToLevel();
    }

    public Generate() {
        this.line = random.nextInt((20 - 3) + 1) + 3;
        this.row = random.nextInt((20 - 5) + 1) + 5;
        System.out.println(line +" ; "+ row);
        createMap();
        contentToLevel();
    }

    private void createMap() {
        for(int i=0; i < line; i++) {
            String strLine = "";
            if(i == 0 || i == (line-1)) {
                for(int n=0; n < row; n++){
                    strLine += "#";
                }
            } else {
                for(int n=0; n < row; n++){
                    if(n == 0 || n == (row-1)) {
                        strLine += "#";
                    }else {
                        strLine += " ";
                    }
                }
            }
            content.add(strLine);
        }
    }

    private void contentToLevel() {
        if(this.line < this.row) {
            numberOfObject = random.nextInt((line - 1) + 1) + 1;
        }else {
            numberOfObject = random.nextInt((row - 1) + 1) + 1;
        }

        int[] posPlayer = randomPosition();
        String lineMod = modifierLine(content.get(posPlayer[0]), posPlayer, '@');
        content.set(posPlayer[0], lineMod);

        int boucle = 0;
        do {
            int[] posObjectif = randomPosition();
            lineMod = modifierLine(content.get(posObjectif[0]), posObjectif, '.');
            if(lineMod != null) {
                content.set(posObjectif[0], lineMod);
                boucle++;
            }
            validPosition(posObjectif);
        }
        while(boucle != numberOfObject);

        boucle = 0;
        do {
            int[] posCaisse = randomPosition();
            lineMod = modifierLine(content.get(posCaisse[0]), posCaisse, '$');
            if(lineMod != null && tabPosVal[posCaisse[0]][posCaisse[1]]) {
                content.set(posCaisse[0], lineMod);
                boucle++;
            }
        }
        while(boucle != numberOfObject);
    }

    private String modifierLine(String oldLine, int[] posObject, char textureObject) {
        String newLine = "";
        for(int i=0; i < row; i++) {
            if(i == posObject[1]) {
                if(oldLine.charAt(i) == ' ') {
                    newLine += textureObject;
                }else {
                    return null;
                }
            }else {
                newLine += oldLine.charAt(i);
            }
        }
        return newLine;
    }

    private int[] randomPosition() {
        int[] posObject = new int[2];

        posObject[0] = random.nextInt(line -2) + 1;
        posObject[1] = random.nextInt(row -2) + 1;

        return posObject;
    }

    private void validPosition(int[] posObjectif) {
        if(tabPosVal == null) {
            tabPosVal = new boolean[line][row];
        }
        for(int i=0; i < line; i++) {
            for(int n=0; n < row; n++) {
                //Si on est sur l'objectif
                if(i == posObjectif[0] && n == posObjectif[1]) {
                    nVP(i, n);
                    //Si on est sur un mur
                }else if(content.get(i).charAt(n) == '#') {
                    nVP(i, n);
                }else {
                    //Si on est sur la meme ligne que l'objectif
                    if(i == posObjectif[0] && (content.get(i-1).charAt(n) == '#'
                            || content.get(i+1).charAt(n) == '#')) {
                        //Si on est sur le bords de la ligne il y a un mur (on ne saura pas pousser la caisse)
                        if(content.get(i).charAt(n-1) == '#' || content.get(i).charAt(n+1) == '#') {
                            nVP(i, n);
                        }else {
                            vP(i, n);
                        }
                        //Si on est sur la meme colone que l'objectif
                    }else if(n == posObjectif[1] && (content.get(i).charAt(n-1) == '#'
                            || content.get(i).charAt(n+1) == '#')) {
                        //Si on est sur le bords de la colone il y a un mur (on ne saura pas pousser la caisse)
                        if(content.get(i+1).charAt(n) == '#' || content.get(i-1).charAt(n) == '#') {
                            nVP(i, n);
                        }else {
                            vP(i, n);
                        }
                        //Si on est a coté d'un mur (Comme pas sur la ligne ou la colone de l'objectif on ne saura pas pousser la caisse)
                    }else if(content.get(i-1).charAt(n) == '#' || content.get(i+1).charAt(n) == '#'
                            || content.get(i).charAt(n-1) == '#' || content.get(i-1).charAt(n+1) == '#') {
                        nVP(i, n);
                    }else {
                        vP(i, n);
                    }
                }
            }
        }
    }

    private void vP(int i, int n) {
        if(tabPosVal[i][n] != true) {
            tabPosVal[i][n] = true;
        }
    }

    private void nVP(int i, int n) {
        if(tabPosVal[i][n] != true && tabPosVal[i][n] != false) {
            tabPosVal[i][n] = false;
        }
    }

    public void printDeadNodes(){
        for (int i=0;i<tabPosVal.length;i++){
            String line = "";
            for (int j=0;j<tabPosVal.length;j++){
                boolean h = tabPosVal[i][j];
                if (h == true){
                    line += "true  ";
                }else if (h == false){
                    line += "false ";
                }else{
                    line += tabPosVal[i][j];
                }
            }
            System.out.println(line);
        }
    }
}