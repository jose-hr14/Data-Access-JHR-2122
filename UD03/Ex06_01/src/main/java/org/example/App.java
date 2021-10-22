package org.example;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        //21
        String url = "jdbc:postgresql://localhost:5432/VTInstitute";
        String user = "postgres";
        String password = "postgres";
        Scanner scanner = new Scanner(System.in);
        String subjectName = " ";
        int subjectYear;
        int subjectHours;

        try {


            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subjects VALUES (DEFAULT , ?,  ?, ?)");

            while(!subjectName.equals("exit"))
            {
                System.out.print("Type subject name o type exit to close the program: ");
                subjectName = scanner.nextLine();
                if(!subjectName.equals("exit"))
                {
                    System.out.print("Type subject year: ");
                    subjectYear = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Type subject hours: ");
                    subjectHours = scanner.nextInt();
                    scanner.nextLine();
                    
                    preparedStatement.setString(1, subjectName);
                    preparedStatement.setInt(2, subjectYear);
                    preparedStatement.setInt(3, subjectHours);
                    preparedStatement.executeUpdate();
                }
            }
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (InputMismatchException inputMismatchException)
        {
            inputMismatchException.printStackTrace();
        }

    }
}
