package org.Ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "postgres";
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            boolean isExecuted = statement.execute("ALTER TABLE subjects ADD hours int");
            System.out.println("Executed successfully: " + isExecuted);

            con.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
