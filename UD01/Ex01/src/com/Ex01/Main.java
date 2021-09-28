package com.Ex01;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        This program reads characters inserted by the keyboard,
        and stores them in a string, counts whose are the biggest and the smallest according to the ASCII table
        and also, stores del number of capital letters
        */
        String letters = "";
        int numLetters = 0;
        char minLetter = Character.MIN_VALUE;
        char maxLetter = Character.MAX_VALUE;
        int numCapitalLetters = 0;
        Scanner keyboard = new Scanner(System.in);

        boolean exit = false;

        while (numLetters < 10 && !exit)
        {
            //Leo una letra
            System.out.println("Type a letter. Type 0 to exit: ");
            char auxLetter = keyboard.next().charAt(0);
            System.out.println();
            System.out.println("-------");
            letters += auxLetter;

            if (letters.charAt(numLetters) == '0')
                exit = true;

            //Stores the biggest and the smallest character
            if (minLetter > auxLetter)
            {
                minLetter = auxLetter;
            }
            if (maxLetter < auxLetter)
            {
                maxLetter = auxLetter;
            }

            //Increments de counter of letters
            numLetters++;
        }

        if (numLetters > 50)
            System.out.println("The string of characters is full");

        //For each char of the string
        for (int i = 0; i < numLetters && letters.charAt(i) != '0'; i++)
        {
            //If the char is a capital letter
            if ((letters.charAt(i) >= 'A') && (letters.charAt(i) <= 'Z'))
            {
                //It accounts the number of capital letters
                numCapitalLetters++;
            }
        }

        //Prints the result
        System.out.println("the smallest char is : " + minLetter);
        System.out.println("the biggest char is : " + maxLetter);
        System.out.println("There are " + numCapitalLetters + " capital letters ");
        keyboard.nextLine();
        keyboard.close();
    }
}

