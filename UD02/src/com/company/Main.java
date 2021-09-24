package com.company;

import java.util.Scanner;

public class Main {
    public static void Ej2_3_1()
    {
        	/*
	Write a java program that reads separately your
	name and surnames and welcomes you using your
	full name (“Hello, Jose Ramón García!”)
	 */
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Write your name: ");
        String name = keyboard.next();
        System.out.print("Write your surname: ");
        String surname = keyboard.next();

        System.out.println(name + " " + surname);
    }

    public static void Ej2_3_2()
    {
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Write a day: ");
        int day = keyboard.nextInt();
        System.out.print("Write a month: ");
        int month = keyboard.nextInt();
        System.out.print("Write a year: ");
        int year = keyboard.nextInt();

        if(day < 1 || day > 31)
            System.out.println("The day is out of range");
        if(month < 1 || month > 12)
            System.out.println("The month is out of range");
    }

    public static void main(String[] args) {

    }
}
