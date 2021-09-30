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
            boolean found = false;
            int ocurrences = 0;


            do{
                index = text.indexOf(word, ocurrences);
                if(index != -1)
                {
                    System.out.println("The word was found at offset " + (index));
                    //System.out.println("The word was found at offset " + (index + totalIndex));
                    found = true;
                    ocurrences = index + word.length();
                }

                //text = text.substring(index + word.length());
                //totalIndex += index;
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
    public static void Ex03Aux()
    {
        try {
            Scanner keyboard = new Scanner(System.in);
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\Ex030503.txt");

            System.out.print("Type the word you want to search: ");
            String word = keyboard.next();

            int byteRead = fileInputStream.read();
            int position = 0;
            while(byteRead != -1)
            {
                if(byteRead == word.charAt(0))
                {
                    for(int i = 1; i < word.length(); i++)
                    {

                        if(byteRead == word.charAt(i))
                        {

                        }
                    }
                }
                byteRead = fileInputStream.read();
                position += 1;
            }





        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
