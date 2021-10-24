package org.Ex03;

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
        try(Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement()) {
            boolean isExecuted = statement.execute("ALTER TABLE subjects ADD hours int");
            System.out.println("Executed successfully: " + isExecuted);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }
}
