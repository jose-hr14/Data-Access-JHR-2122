package org.Ex04;

import java.sql.*;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main(String[] args) {
        Ex02("20");
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

    public static void Ex02(String deptno) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            CallableStatement callableStatement = connection.prepareCall("SELECT ex02(" + "'" + deptno + "'" + ")");
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
