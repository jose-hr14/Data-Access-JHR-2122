package org.example;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    public void addStudent(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setString(1,student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getIdCard());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            if(throwable.getSQLState().equals("23505"))
            {
                throw throwable;
            }
        }
    }

    public void addCourse(Course course) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course VALUES(?, ?)");
            preparedStatement.setInt(1,course.getCode());
            preparedStatement.setString(2, course.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void addSubject(Subject subject) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subjects VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setInt(1,subject.getCode());
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setInt(3, subject.getYear());
            preparedStatement.setInt(4, subject.getCourseID());
            preparedStatement.setInt(5, subject.getHours());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void addStudentList(ArrayList<Student> studentList)
    {
        for (Student student:studentList)
        {
            try {
                addStudent(student);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addCourseList(ArrayList<Course> courseList)
    {
        for (Course course: courseList)
        {
            addCourse(course);
        }
    }

    public void addSubjectList(ArrayList<Subject> subjectList)
    {
        for (Subject subject: subjectList)
        {
            addSubject(subject);
        }
    }

    public void enrollStudent(Student student, Course course)
    {
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrollment VALUES(DEFAULT, ?, ?)");
            preparedStatement.setString(1, student.getIdCard());
            preparedStatement.setInt(2, course.getCode());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void addScore(Student student, Course course)
    {
        //insert into scores select enrollment.code, s.code, 0 from enrollment inner join course c on enrollment.course = c.code inner join subjects s on c.code = s.courseid
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into scores select enrollment.code, s.code, 0 from enrollment inner join course c on c.code = enrollment.course inner join subjects s on enrollment.course = s.courseid where student = ? and course = ?");
            preparedStatement.setString(1, student.getIdCard());
            preparedStatement.setInt(2, course.getCode());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void importXML(ArrayList<Student> studentList, ArrayList<Course> courseList, ArrayList<Subject> subjectList) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            for (Student student:studentList) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");
                preparedStatement.setString(1,student.getFirstName());
                preparedStatement.setString(2, student.getLastName());
                preparedStatement.setString(3, student.getIdCard());
                preparedStatement.setString(4, student.getEmail());
                preparedStatement.setString(5, student.getPhone());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            for (Course course: courseList) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course VALUES(?, ?)");
                preparedStatement.setInt(1,course.getCode());
                preparedStatement.setString(2, course.getName());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            for (Subject subject: subjectList) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO subjects VALUES(?, ?, ?, ?, ?)");
                preparedStatement.setInt(1,subject.getCode());
                preparedStatement.setString(2, subject.getName());
                preparedStatement.setInt(3, subject.getHours());
                preparedStatement.setInt(4, subject.getCourseID());
                preparedStatement.setInt(5, subject.getYear());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }

    }
    public String retrieveReport(Student student){
        ArrayList<String> report = new ArrayList<>();
        String reportt = "";
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("select c.name, s2.name, s.score from enrollment inner join scores s on enrollment.code = s.enrollmentid inner join subjects s2 on s2.code = s.subjectid inner join course c on c.code = s2.courseid where enrollment.student = ?");
            statement.setString(1, student.getIdCard());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                reportt += result.getString(1) + " - " + result.getString(2) + ": " + result.getInt(3) + " \r\n";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reportt;
    }

    public ArrayList<Student> retrieveStudentList(){
        ArrayList<Student> studentList = new ArrayList<Student>();
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("SELECT * from student");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                studentList.add(new Student(result.getString(1),result.getString(2), result.getString(3), result.getString(4),result.getString(5) ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentList;
    }

    public ArrayList<Student> retrieveEnrolledStudentsList()
    {
        ArrayList<Student> studentList = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student inner join enrollment e on student.idcard = e.student;");
            ResultSet result = preparedStatement.executeQuery();
            while(result.next())
            {
                studentList.add(new Student(result.getString(1),result.getString(2), result.getString(3), result.getString(4),result.getString(5) ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentList;
    }

    public ArrayList<Course> retrieveCourseList(){
        ArrayList<Course> courseList = new ArrayList<Course>();
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("SELECT * from course");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                courseList.add(new Course(result.getInt(1),result.getString(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseList;
    }

    public ArrayList<Subject> retrieveSubjectList(){
        ArrayList<Subject> subjectList = new ArrayList<Subject>();
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("SELECT * from subjects");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                subjectList.add(new Subject(result.getInt(1),result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    public ArrayList<Subject> retrieveSubjectList(int courseID){
        ArrayList<Subject> subjectList = new ArrayList<Subject>();
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("SELECT * from subjects where courseid = ?");
            statement.setInt(1, courseID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                subjectList.add(new Subject(result.getInt(1),result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectList;
    }

    public boolean isEnrrolled(Student student, Course course)
    {
        boolean isEnrrolled = true;
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            //PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from enrollment where student = ? AND course = ?");
            PreparedStatement preparedStatement = connection.prepareStatement("select isenrolled(?, ?)");
            preparedStatement.setString(1, student.getIdCard());
            preparedStatement.setInt(2, course.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1) == 0)
            {
                isEnrrolled = false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  isEnrrolled;
    }

    public boolean hasPassedCourses(Student student)
    {
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            //PreparedStatement preparedStatement = connection.prepareStatement("select s.score from enrollment inner join scores s on enrollment.code = s.enrollmentid inner join subjects s2 on s2.code = s.subjectid where student = ? and s.score < 5");
            PreparedStatement preparedStatement = connection.prepareStatement("select failedsubjects(?)");
            preparedStatement.setString(1, student.getIdCard());
            //preparedStatement.setInt(2, course.getCode());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1) == 0)
                return true;
            /*
            while(resultSet.next())
            {
                if(resultSet.getInt(1) < 5)
                {
                    return false;
                }
            }
            */

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
