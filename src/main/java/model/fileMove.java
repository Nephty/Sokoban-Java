package main.java.model;

import java.io.*;
import java.util.ArrayList;

public class fileMove {
    
    public static ArrayList<String> readFile(String filePath){
        ArrayList<String> res = new ArrayList<String>();
        try{
            File file = new File(filePath);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                res.add(line);
            }
            fr.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return res;
    }
}
