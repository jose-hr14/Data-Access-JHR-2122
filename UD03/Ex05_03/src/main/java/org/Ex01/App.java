package org.Ex01;

import java.sql.*;

public class App
{
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main( String[] args )
    {
        try(Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM subjects ORDER BY code")) {
            System.out.println("Code" + "\t" + "Year"+ "\t" + "Name");
            System.out.println("-------------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getString(1) + "\t\t " + rs.getString(3) + "\t\t" + rs.getString(2));
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }
}
