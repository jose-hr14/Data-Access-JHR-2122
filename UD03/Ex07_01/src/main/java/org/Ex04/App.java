package org.Ex04;

import java.sql.*;
import java.util.Scanner;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        String option = "";
        while(!option.equals("0"))
        {
            System.out.print("Choose an option: ");
            option = keyboard.nextLine();
            switch (option)
            {
                case "1":
                    Ex01Handler();
                    break;
                case "2":
                    Ex02Handler();
                    break;
                case "3":
                    break;
                case "0":
                    break;
            }
        }



    }
    public static void Ex01Handler(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Type a job to show it's employees: ");
        String job = new Scanner(System.in).nextLine();
        Ex01(job);
        System.out.println();
    }
    public static void Ex02Handler(){
        try{
            System.out.print("Type a department number to show it's employees: ");
            String departmentNumberString = new Scanner(System.in).nextLine();
            int departmentNumber = Integer.parseInt(departmentNumberString);
            Ex02(departmentNumber);
            System.out.println();
        }
        catch (NumberFormatException numberFormatException){
            System.out.println("You didn't introduce a number");
        }

    }
    public static void Ex01(String job) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement callableStatement = connection.prepareCall("SELECT ex01(" + "'" + job + "'" + ")");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String[] resultado = resultSet.getString(1).split(",");
                System.out.println(resultado[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Ex02(int deptno) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement callableStatement = connection.prepareCall("SELECT ex02(" + deptno + ")");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String[] resultado = resultSet.getString(1).split(",");
                System.out.println(resultado[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Ex03(String name) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement callableStatement = connection.prepareCall("SELECT ex03(" + "'" + name + "'" + ")");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                String[] resultado = resultSet.getString(1).split(",");
                System.out.println(resultado[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
