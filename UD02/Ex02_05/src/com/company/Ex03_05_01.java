package com.company;

import java.io.*;

public class Ex03_05_01 {
    public static void Ex01()
    {
        try
        {
            //InputStream inputStream = new FileInputStream("C:\\Ficheros\\UD02Ex03_05_01.txt");
            //InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.jpg");
            InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.bmp");
            //OutputStream outputStream = new FileOutputStream("C:\\Ficheros\\UD02Ex05_01_02.txt");

            byte[] buffer = new byte[4];
            String hexValues = "";
            int c = inputStream.read(buffer, 0, 4) & 0xFF;
            String hexValue = "";


            for(int i = 0; i < buffer.length; i++)
            {
                System.out.print(Integer.toHexString(0xFF & buffer[i]));
                hexValues += Integer.toHexString(0xFF & buffer[i]);
            }
            System.out.println();


            switch (hexValues)
            {
                case "ffd8ffe0":
                    System.out.println("JPG");
                    break;
                case "424%":
                    System.out.println("BMP");
                    break;
                case "47494638":
                    System.out.println("GIF");
                    break;
                case "0010":
                    System.out.println("ICO");
                    break;
                case "89504e47":
                    System.out.println("PNG");
                    break;
            }

            inputStream.close();
            //outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
