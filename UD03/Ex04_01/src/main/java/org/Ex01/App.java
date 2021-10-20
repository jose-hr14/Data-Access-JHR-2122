package org.Ex01;

import java.sql.*;

public class App
{
    public static void main( String[] args ) {

        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String SQLsentence = "SELECT * FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Year"+ "\t" + "Name");
            System.out.println("-------------------------------------------");
            while (rs.next())
            {
                System.out.println(rs.getString(1) + "\t\t " + rs.getString(3) + "\t\t" + rs.getString(2));
            }
            rs.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
