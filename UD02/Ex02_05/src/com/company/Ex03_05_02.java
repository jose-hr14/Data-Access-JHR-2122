package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//byte0 + 2^8 * byte1 + 2^16 * byte2 + 2^24 * byte3
//byte0 * 2^24

public class Ex03_05_02 {

    public static void Ex02() {
        try
        {
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\img.bmp");
            //InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\sample_640×426.bmp");

            byte[] header = new byte[54];
            fileInputStream.read(header, 0, 54);

            for(int i = 0; i < header.length; i++)
            {
                System.out.print(Integer.toHexString(header[i]  & 0xFF));
            }
            System.out.println();

            String hexNum = "";
            int num;
            for (int i = 5; i >= 2; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            int value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP size: " + value);

            for (int i = 21; i >= 18; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP width: " + value);


            for (int i = 25; i >= 22; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP height: " + value);

            for (int i = 33; i >= 30; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            switch (value)
            {
                case 0:
                    System.out.println("Compression: " + value + " none");
                    break;
                case 1:
                    System.out.println("Compression: " + value + " RLE-8");
                    break;
                case 2:
                    System.out.println("Compression: " + value + " RLE-4");
                    break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Ex02SecondAttemp()
    {
        try
        {
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\img.bmp");
            //InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\sample_640×426.bmp");

            byte[] header = new byte[54];
            fileInputStream.read(header, 0, 54);

            for(int i = 0; i < header.length; i++)
            {
                System.out.print(Integer.toHexString(header[i]  & 0xFF));
            }
            System.out.println();

            String hexNum = "";
            int num;
            for (int i = 5; i >= 2; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            int value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP size: " + value);
            int numRandom = Byte.toUnsignedInt(header[4]);
            int size = Byte.toUnsignedInt(header[2])  + Byte.toUnsignedInt(header[3])  * 2^8 + Byte.toUnsignedInt(header[4])  * 2^16 + Byte.toUnsignedInt(header[5])  * 2^24;

            for (int i = 21; i >= 18; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP width: " + value);


            for (int i = 25; i >= 22; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            System.out.println("BMP height: " + value);

            for (int i = 33; i >= 30; i--)
            {
                num = Byte.toUnsignedInt(header[i]);
                hexNum += Integer.toHexString(num);
            }
            value = Integer.parseInt(hexNum, 16);
            hexNum = "";
            switch (value)
            {
                case 0:
                    System.out.println("Compression: " + value + " none");
                    break;
                case 1:
                    System.out.println("Compression: " + value + " RLE-8");
                    break;
                case 2:
                    System.out.println("Compression: " + value + " RLE-4");
                    break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
