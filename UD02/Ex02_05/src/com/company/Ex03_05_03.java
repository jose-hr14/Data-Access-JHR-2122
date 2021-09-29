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
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\Ex030503.txt");

            System.out.print("Type the word you want to search: ");
            String word = keyboard.next();

            byte[] docText = fileInputStream.readAllBytes();
            String text = "";

            for(int i = 0; i < docText.length; i++)
            {
                text += (char)docText[i];
            }

            int index = 0;
            int totalIndex = 0;


            do{
                index = text.indexOf(word);
                if(index != -1)
                {
                    System.out.println("The word was found at offset " + (index + totalIndex));
                }
                else
                {
                    System.out.println("The word was not found");
                }
                text = text.substring(index + word.length());
                totalIndex += index;
            }while(index != -1);





        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
