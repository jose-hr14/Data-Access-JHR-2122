package com.Ex03_05;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main
{
    public static void main(String[] args)
    {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("C:\\Ficheros\\img.bmp");

            String datos = "";
            byte[] allBytes = inputStream.readAllBytes();
            for(int i = 2; i < 2 + 2; i++)
            {
                datos += allBytes[i] & 0xFF;
            }

            byte[] sizeBytes = new byte[2];
            inputStream.skip(2);
            inputStream.read(sizeBytes, 0, 2);

            inputStream.skip(18);
            byte[] widthBytes = new byte[4];
            inputStream.read(widthBytes, 18, 4);


            byte[] heightBytes = new byte[4];
            inputStream.read(heightBytes, 22, 4);

            byte[] isCompressed = new byte[4];
            inputStream.read(isCompressed, 40, 4);

            for(byte b : sizeBytes)
            {
                System.out.print((char)b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
