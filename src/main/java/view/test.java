package main.java.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> content = new ArrayList<>();
        File textFile = new File("C:\\Users\\Nephty\\Desktop\\fileA.txt");
        Scanner dataScanner = new Scanner(textFile);
        while (dataScanner.hasNext()) {
            content.add(dataScanner.nextLine());
        }
        dataScanner.close();

        for (String str : content) {
            System.out.println(str);
        }
    }
}
