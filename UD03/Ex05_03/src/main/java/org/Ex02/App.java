package org.Ex02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App
{
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main( String[] args )
    {
        try(Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement()) {
            int executedUpdates = statement.executeUpdate("INSERT INTO subjects VALUES (DEFAULT , 'MARKUP LANGUAGES',  1)");
            System.out.println("Number of updates done: " + executedUpdates);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }
}
