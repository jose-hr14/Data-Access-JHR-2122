package org.Ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement preparedStatement = connection.prepareStatement("ALTER TABLE subjects DROP COLUMN course");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("ALTER TABLE subjects ADD course INT");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("UPDATE subjects SET course = ?");
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("ALTER TABLE subjects ADD FOREIGN KEY (course) REFERENCES courses(code)");
            preparedStatement.executeUpdate();
            
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
