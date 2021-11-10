package org.example;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//.setModel(new DefaultComboBox(arrayList.toArray())=
public class GUI {
    private JPanel panel;
    private JTabbedPane tabbedPane;
    private JPanel studentsTab;
    private JPanel enrollmentsTab;
    private JPanel reportsTab;
    private JPanel utilitiesTab;
    private JTextField studentFirstNameTextField;
    private JTextField studentLastNameTextField;
    private JTextField studentIDTextField;
    private JTextField studentEmailTextField;
    private JTextField studentPhoneTextField;
    private JButton addButton;
    private JLabel resultLabel;
    private JComboBox studentComboBox;
    private JComboBox courseComboBox;
    private JButton enrrollButton;
    private JComboBox studentReportComboBox;
    private JTextPane reportsTextPane;
    private JButton printButton;
    private JButton importXMLButton;
    private JLabel xmlImportLabel;
    private JLabel enrollmentLabel;

    public GUI() {
        refreshComboBox();
        refreshReportsPane();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!studentFirstNameTextField.getText().isBlank() || !studentLastNameTextField.getText().isBlank() || !studentIDTextField.getText().isBlank())
                    {
                        Student student = new Student(studentFirstNameTextField.getText(), studentLastNameTextField.getText(), studentIDTextField.getText(), studentEmailTextField.getText(), studentPhoneTextField.getText());
                        new Database().addStudent(student);
                        resultLabel.setText("Student saved correctly");
                        refreshComboBox();
                    }
                    else
                    {
                        resultLabel.setText("One or more textbox is blank");
                    }
                } catch (NumberFormatException numberFormatException) {
                    resultLabel.setText("ID Card must be a number");
                } catch (SQLException throwables) {
                    resultLabel.setText("Student already exits");
                }
            }
        });
        enrrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) studentComboBox.getSelectedItem();
                Course course = (Course) courseComboBox.getSelectedItem();
                Database database = new Database();

                if(!database.isEnrrolled(student, course) && database.hasPassedCourses(student))
                {
                    new Database().enrollStudent(student, course);
                    new Database().addScore(student, course);
                    enrollmentLabel.setText("Student enrolled successfully");
                }
                else
                    enrollmentLabel.setText("Student is already enrolled in this course o hasn't passed a previous courses");
            }
        });
        studentReportComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshReportsPane();
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = null;
                if(!reportsTextPane.getText().isBlank())
                {
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.showSaveDialog(new JFrame());
                    file = jFileChooser.getSelectedFile();
                }

                if(file != null)
                {
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        Student student = (Student) studentReportComboBox.getSelectedItem();
                        fileWriter.write("-- " + student.firstName + " " + student.lastName + " Marks -- \r\n");
                        fileWriter.write(reportsTextPane.getText());
                        fileWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        importXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(new JFrame());
                File file = jFileChooser.getSelectedFile();
                if(file != null)
                {
                    try {
                        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                        XMLReader xmlReader = new XMLReader();
                        saxParser.parse(file, xmlReader);
                        Database database = new Database();
                        ArrayList<Student> studentList = xmlReader.getStudentList();
                        ArrayList<Course> courseList = xmlReader.getCourseList();
                        ArrayList<Subject> subjectList = xmlReader.getSubjectList();
                        database.importXML(studentList, courseList, subjectList);
                        refreshComboBox();
                        xmlImportLabel.setText("Data imported successfully");
                    } catch (ParserConfigurationException | SAXException | IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException throwable) {
                        throwable.printStackTrace();
                        xmlImportLabel.setText("Data couldn't be imported, aborting transaction");
                    }
                }
            }
        });
    }
    public void refreshReportsPane()
    {
        Student student = (Student) studentReportComboBox.getSelectedItem();
        String report = new Database().retrieveReport(student);
        reportsTextPane.setText(report);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setVisible(true);

        /*
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("import.xml", new XMLReader());
        } catch ( Exception e ) {
            e.printStackTrace();
        }

         */

    }
    private void refreshComboBox()
    {
        ComboBoxModel studentModel = new DefaultComboBoxModel((new Database().retrieveStudentList().toArray()));
        ComboBoxModel courseModel = new DefaultComboBoxModel(new Database().retrieveCourseList().toArray());
        studentComboBox.setModel(studentModel);
        courseComboBox.setModel(courseModel);
        studentReportComboBox.setModel(studentModel);
    }


}
