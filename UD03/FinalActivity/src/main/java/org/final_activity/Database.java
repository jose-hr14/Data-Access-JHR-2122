package org.final_activity;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles everything related with the database, including reading from the database and writing to the
 * database.
 * @author José Hernández Riquelme
 */
public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitute";
    static final String user = "postgres";
    static final String password = "postgres";

    /**
     * Adds a new student to the database. If the student already exits, throws a SQL Exception that will be
     * caught from the main form to inform the user.
     * @param student
     * @throws SQLException
     */
    public void addStudent(Student student) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setString(1,student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getIdCard());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getPhone());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Enrolls a new student in the database, saving its student id and course code in the enrollment table.
     * @param student
     * @param course
     */
    public void enrollStudent(Student student, Course course)
    {
        try(Connection connection = DriverManager.getConnection(url, user, password))
        {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO enrollment VALUES(DEFAULT, ?, ?)");
            preparedStatement.setString(1, student.getIdCard());
            preparedStatement.setInt(2, course.getCode());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException throwable) {}
    }

    /**
     * Saves all the subjects from the enrolled course, together with the enrolment id of the student.
     * @param student
     * @param course
     */
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

    /**
     * It parses the read xml file to extract the students, courses or subjects read on it, and saves them in the 
     * database using a transaction. If the parsing fails or an SQL Exception occurs, it will throw and exception
     * that will be caught in the main form to inform the user.
     * @param studentList
     * @param courseList
     * @param subjectList
     * @throws SQLException
     */
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
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }
    }
    /**
     * Retrieves the report of the students that receives as parameter and returns it
     * @param student student whose reports will be returned
     * @return a string with the whole report of the student
     */
    public String retrieveReport(Student student){
        StringBuilder report = new StringBuilder();
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            PreparedStatement statement = connection.prepareStatement("select c.name, s2.name, s.score from enrollment inner join scores s on enrollment.code = s.enrollmentid inner join subjects s2 on s2.code = s.subjectid inner join course c on c.code = s2.courseid where enrollment.student = ?");
            statement.setString(1, student.getIdCard());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                report.append(result.getString(1)).append(" - ").append(result.getString(2)).append(": ").append(result.getInt(3)).append(" \r\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return report.toString();
    }

    /**
     * 
     * @return
     */
    public ArrayList<Student> retrieveStudentList(){
        ArrayList<Student> studentList = new ArrayList<>();
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
            boolean exits;
            while(result.next())
            {
                exits = false;
                Student student = new Student(result.getString(1),result.getString(2), result.getString(3), result.getString(4),result.getString(5) );
                for (Student studentCompare: studentList) {
                    if(student.getIdCard().equals(studentCompare.getIdCard()))
                    {
                        exits = true;
                        break;
                    }
                }
                if(!exits)
                    studentList.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentList;
    }

    public ArrayList<Course> retrieveCourseList(){
        ArrayList<Course> courseList = new ArrayList<>();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
