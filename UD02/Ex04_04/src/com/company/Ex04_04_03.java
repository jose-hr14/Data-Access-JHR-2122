package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ex04_04_03 {
    public static void Ex03()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Type the doc name: ");
        String name = keyboard.nextLine();
        String line = "";
        int count = 1;



        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            while((line = reader.readLine()) != null)
            {
                System.out.println(line);
                if(count % 23 == 0)
                    keyboard.nextLine();
                count++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
