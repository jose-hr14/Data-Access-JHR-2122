package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class Ex03_05_03 {
    public static void Ex03()
    {
        try {
            Scanner keyboard = new Scanner(System.in);
            String docText = new String( new FileInputStream("C:\\Ficheros\\Ex030503.txt").readAllBytes() );

            System.out.print("Type the word you want to search: ");
            String word = keyboard.next();

            int index = 0;
            boolean found = false;
            int startIndex = 0;

            do{
                index = docText.indexOf(word, startIndex);
                if(index != -1)
                {
                    System.out.println("The word was found at offset " + (index));
                    //System.out.println("The word was found at offset " + (index + totalIndex));
                    found = true;
                    startIndex = index + word.length();
                }
            }while(index != -1);

            if(!found)
            {
                System.out.println("The word was not found");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
