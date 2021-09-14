package com.Ex01;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	/*
Este programa lee caracteres introducidos por teclado,
los almacena en una cadena, calcula cuales son los mayores y menores atendiendo a la tabla ASCII * También almacena el número de ellos que son mayúsculas
*/
        String Letras = "";
        int numLetras = 0;
        char minLetra = Character.MIN_VALUE;
        char maxLetra = Character.MAX_VALUE;
        int numMayusculas = 0;
        Scanner keyboard = new Scanner(System.in);

        boolean salir = false;

        while (numLetras < 10 && !salir)
        {
            //Leo una letra
            System.out.println("Introduce una letra. Pulsa 0 si quieres salir: ");
            char letraAux = keyboard.next().charAt(0);
            System.out.println();
            System.out.println("-------");
            Letras = Letras + Character.toString(letraAux);

            if (Letras.charAt(numLetras) == '0')
                salir = true;

            //almaceno los menores y mayores.
            if (minLetra > letraAux)
            {
                minLetra = letraAux;
            }
            if (maxLetra < letraAux)
            {
                maxLetra = letraAux;
            }

            //Incremento el contador de letras
            numLetras++;
        }

        if (numLetras > 50)
            System.out.println("El cadena de caracteres esta llena");

        //Para cada char de la cadena
        for (int i = 0; i < numLetras && Letras.charAt(i) != '0'; i++)
        {
            //Si la letra es mayusculas
            if ((Letras.charAt(i) >= 'A') && (Letras.charAt(i) <= 'Z'))
            {
                //contabiliza las letras mayusculas
                numMayusculas++;
            }
        }

        //Escribe el resultado
        System.out.println("el Char menor es : " + minLetra);
        System.out.println("el Char mayor es : " + maxLetra);
        System.out.println("Hay " + numMayusculas + " letras mayusculas ");
        keyboard.next().charAt(0);
        keyboard.close();
    }
}

