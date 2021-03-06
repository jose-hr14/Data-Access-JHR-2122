package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ex04_04_04 {
    public static void Ex04()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Type the doc name: ");
        String name = keyboard.nextLine();
        System.out.print("Type the word you want to search: ");
        String word = keyboard.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            String line;

            while((line = reader.readLine()) != null)
            {
                if(line.contains(word))
                    System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
