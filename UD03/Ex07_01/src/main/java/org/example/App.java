package org.example;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App
{
    static final String url = "jdbc:postgresql://localhost:5432/employees";
    static final String user = "postgres";
    static final String password = "postgres";
    public static void main( String[] args )
    {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            CallableStatement callableStatement = connection.prepareCall("call ex01('SALESMAN')");
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
