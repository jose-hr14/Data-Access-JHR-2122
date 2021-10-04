package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//Ex02 is the correct one
public class Ex03_05_02 {

    public static int readBytesToIntLittleEndian(int offsetStart, int size, byte[] buffer)
    {
        int result = Byte.toUnsignedInt(buffer[offsetStart]);

        for(int i = 1; i < size; i++)
        {
            result += Byte.toUnsignedInt(buffer[offsetStart + i]) * (int) Math.pow(256, i);
        }
        return result;
    }

    public static void Ex02()
    {
        try
        {
            //InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\img.bmp");
            InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\prueba.bmp");
            //InputStream fileInputStream = new FileInputStream("C:\\Ficheros\\sample_640×426.bmp");

            byte[] header = new byte[54];
            fileInputStream.read(header, 0, 54);


            if(header[0] == (byte)0x42 && header[1] == (byte)0x4D)
            {
                int size = readBytesToIntLittleEndian(2, 4, header);
                System.out.println("BMP size: " + size);

                int width = readBytesToIntLittleEndian(18, 4, header);
                System.out.println("BMP width: " + width);

                int height = readBytesToIntLittleEndian(22, 4, header);
                System.out.println("BMP height: " + height);

                int compression = readBytesToIntLittleEndian(30, 4, header);

                switch(compression)
                {
                    case 0:
                        System.out.println("Compression: " + compression + " none");
                        break;
                    case 1:
                        System.out.println("Compression: " + compression + " RLE-8");
                        break;
                    case 2:
                        System.out.println("Compression: " + compression + " RLE-4");
                        break;
                }

            }
            else
            {
                System.out.println("The file read is not a BMP");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Ex02Bad() {
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
}

//byte0 + 2^8 * byte1 + 2^16 * byte2 + 2^24 * byte3
//byte0 * 2^24