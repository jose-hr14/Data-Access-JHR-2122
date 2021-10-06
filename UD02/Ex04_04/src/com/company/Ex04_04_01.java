package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ex04_04_01 {

    public static void Ex01()
    {
        Scanner keyboard = new Scanner(System.in);
        BufferedReader reader;
        PrintWriter printWriter = null;
        boolean append = false;
        String yerOrNo;
        String text = "";

        System.out.print("Enter filename: ");
        String docName = keyboard.nextLine();

        if(new File(docName).exists()) {
            try {
                do {
                    System.out.print("The file exits, do you want to overwrite the content? Y/N ");
                    yerOrNo = keyboard.nextLine();
                } while (!yerOrNo.toUpperCase().equals("Y") && !yerOrNo.toUpperCase().equals("N"));

                if (yerOrNo.toUpperCase().equals("N"))
                    append = true;

                printWriter = new PrintWriter(new FileWriter(docName, append));

                long lines = 1;
                if(append)
                    lines = Files.lines(Paths.get(docName)).count() + 1;

                System.out.println("Enter the text and press enter, or leave a white space and press enter to close the program");
                text = keyboard.nextLine();
                do {
                    printWriter.println(lines + ". " + text);
                    lines++;
                    text = keyboard.nextLine();
                } while (text != "");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                printWriter.flush();
                printWriter.close();
            }

        }
        else
        {
            System.out.println("File does not exits, creating a new one");
            try {
                new FileOutputStream(docName);

                printWriter = new PrintWriter(new FileWriter(docName));
                int lines = 1;

                System.out.println("Enter the text and press enter, or leave a white space and press enter to close the program");
                text = keyboard.nextLine();
                do {
                    printWriter.println(lines + ". " + text);
                    lines++;
                    text = keyboard.nextLine();
                } while (text != "");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                printWriter.flush();
                printWriter.close();
            }
        }



    }

}
