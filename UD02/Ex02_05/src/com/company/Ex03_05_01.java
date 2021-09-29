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
    public static void Ex01Aux()
    {
        try {
            //InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.gif");
            InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.ico");
            //InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.bmp");
            //InputStream inputStream = new FileInputStream("C:\\Ficheros\\img.jpg");

            byte[] header = new byte[6];
            inputStream.read(header, 0, 6);

            if(Byte.toUnsignedInt(header[0]) == 0x42 && Byte.toUnsignedInt(header[1]) == 0x4D)
            {
                System.out.println("This is a BMP");
            }
            if(Byte.toUnsignedInt(header[0]) == 0x47 && Byte.toUnsignedInt(header[1]) == 0x49  && Byte.toUnsignedInt(header[2]) == 0x46 && Byte.toUnsignedInt(header[3]) == 0x38 && Byte.toUnsignedInt(header[4]) == 0x39  && Byte.toUnsignedInt(header[5]) == 0x61)
            {
                System.out.println("This is a GIF");
            }
            if(Byte.toUnsignedInt(header[0]) == 0x00 && Byte.toUnsignedInt(header[1]) == 0x00  && Byte.toUnsignedInt(header[2]) == 0x01 && Byte.toUnsignedInt(header[3]) == 0x00)
            {
                System.out.println("This is a ICO");
            }
            if(Byte.toUnsignedInt(header[0]) == 0xFF && Byte.toUnsignedInt(header[1]) == 0xD8  && Byte.toUnsignedInt(header[2]) == 0xFF)
            {
                System.out.println("This is a JPEG");
            }
            if(Byte.toUnsignedInt(header[0]) == 0x89 && Byte.toUnsignedInt(header[1]) == 0x50  && Byte.toUnsignedInt(header[2]) == 0x4E && Byte.toUnsignedInt(header[3]) == 0x47)
            {
                System.out.println("This is a PNG");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
