package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Ex03_05_01.Ex01();
        //Ex03_05_02.Ex02();

        try {
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\UD02Ex03_05_03.txt");
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Enter the word you want to search: ");
            String word = keyboard.next();

            boolean found = false;
            int offSet = 0;
            int dataRead = fileInputStream.read();
            while(dataRead != -1 && found == false)
            {
                if(word.charAt(0) == (char)dataRead)
                {
                    found = true;
                    for(int i = 1; i < word.length() && found == true; i++)
                    {
                        dataRead = fileInputStream.read();
                        offSet += 1;
                        if(word.charAt(i) != (char)dataRead)
                            found = false;
                    }
                }

                dataRead = fileInputStream.read();
                if(found == false)
                    offSet += 1;

            }
            System.out.println(found);
            System.out.println(offSet);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
