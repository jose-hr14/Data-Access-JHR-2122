package org.Ex02;

import java.sql.*;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public static void main(String[] args) {
        String sentence = "CREATE TABLE courses (code SERIAL, name VARCHAR(90))";
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS courses");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("CREATE TABLE courses (code SERIAL PRIMARY KEY, name VARCHAR(90))");
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("INSERT INTO courses (name) VALUES (?)");
            preparedStatement.setString(1, "Multiplatform app development");
            preparedStatement.executeUpdate();
            preparedStatement.setString(1, "Web development");
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
