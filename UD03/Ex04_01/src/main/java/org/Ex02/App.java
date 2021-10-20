package org.Ex02;

import java.sql.*;

public class App {
    public static void main(String[] args) {

        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            int executedUpdates = statement.executeUpdate("INSERT INTO subjects VALUES (DEFAULT , 'MARKUP LANGUAGES',  1)");
            System.out.println("Number of updates done: " + executedUpdates);

            con.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
