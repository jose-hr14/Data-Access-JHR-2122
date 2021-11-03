package org.example;

import java.sql.*;

public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public void addStudent(Student student){
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setString(1,student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getIdCard());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            if(throwable.getSQLState().equals("23505"))
            {
                System.out.println("This student already exits in the datebase");
            }
        }
    }
}
