package com.company;

import java.io.*;
import java.util.Scanner;

public class Ex04_04_02 {
    public static void Ex02()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter filename 01: ");
        String docName01 = keyboard.nextLine();
        docName01 = "01.txt";
        System.out.print("Enter filename 02: ");
        String docName02 = keyboard.nextLine();
        docName02 = "02.txt";


        BufferedReader reader01 = null;
        BufferedReader reader02 = null;
        PrintWriter printWriterOut = null;

        try {
            reader01 = new BufferedReader(new FileReader(docName01));
            reader02 = new BufferedReader(new FileReader(docName02));
            printWriterOut = new PrintWriter(new BufferedWriter(new FileWriter("sorted.txt")));

            String text01 = "";
            String text02 = "";

            while((text01 = reader01.readLine()) != null)
            {
                text02 = reader02.readLine();
                if(text01.compareTo(text02) < 0)
                {
                    printWriterOut.println(text01);
                    printWriterOut.println(text02);
                }
                else if(text01.compareTo(text02) > 0)
                {
                    printWriterOut.println(text02);
                    printWriterOut.println(text01);
                }
                else
                {
                    printWriterOut.println(text01);
                    printWriterOut.println(text02);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            printWriterOut.flush();
            printWriterOut.close();
        }

    }
}
