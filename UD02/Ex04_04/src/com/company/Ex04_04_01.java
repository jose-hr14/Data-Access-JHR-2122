package com.company;

import java.io.*;
import java.util.Scanner;

public class Ex04_04_01 {

    public static void Ex04()
    {
        Scanner keyboard = new Scanner(System.in);
        BufferedReader reader;
        PrintWriter writer = null;
        boolean overwrite;
        String yerOrNo = "";

        System.out.print("Enter filename: ");
        String docName = keyboard.nextLine();

        if(new File(docName).exists())
        {
            do{
                System.out.print("Do you want to overwrite the content? Y/N ");
                yerOrNo = keyboard.nextLine();
            }while(yerOrNo != "Y" || yerOrNo != "N");

        }
        else
        {
            System.out.println("File does not exits, creating a new one");
            try {
                new FileOutputStream(docName);

                writer = new PrintWriter(new FileWriter(docName));

                writer.println("Hola");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                writer.flush();
                writer.close();
            }
        }



    }

}
