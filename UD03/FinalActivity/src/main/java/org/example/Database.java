package org.example;

import javax.swing.*;
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

    public void enrollStudent(Student student, Course course)
    {
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrollment VALUES(DEFAULT, ?, ?)");
            preparedStatement.setInt(1, student.getIdCard());
            preparedStatement.setInt(2, course.getCode());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public JComboBox chargeStudentComboBox(JComboBox comboBox) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            comboBox = new JComboBox();

            PreparedStatement statement = connection.prepareStatement("select * from student");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                //comboBox.addItem(new Student(result.getString(1),result.getString(2), result.getInt(3), result.getString(4),result.getString(5) ));
                comboBox.addItem(result.getString(1));
            }

            statement.close();
            result.close();

            return comboBox;
        }
    }

    public JComboBox chargeCourseComboBox(JComboBox comboBox) throws SQLException {
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            comboBox = new JComboBox();

            PreparedStatement statement = connection.prepareStatement("select * from course");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                comboBox.addItem(new Course(result.getInt(1), result.getString(2)));
            }

            statement.close();
            result.close();

            return comboBox;
        }
    }


}
