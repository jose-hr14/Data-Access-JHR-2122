package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Ex04_04_04 {
    public static void Ex04()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("hola.txt"));
            String line;

            long lines  = Files.lines(Paths.get("hola.txt")).count();
            while((line = reader.readLine()) != null)
            {
                //if(line.contains("word to search"))
                    System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
