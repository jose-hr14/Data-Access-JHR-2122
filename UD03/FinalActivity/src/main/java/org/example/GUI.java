package org.example;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

//.setModel(new DefaultComboBox(arrayList.toArray())=
public class GUI {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JPanel studentsTab;
    private JPanel enrollmentsTab;
    private JPanel reportsTab;
    private JPanel utilitiesTab;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton addButton;
    private JLabel resultLabel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton enrrollButton;
    private JComboBox comboBox3;
    private JTextPane textPane1;
    private JButton printButton;
    private JButton importXMLButton;

    public GUI() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!textField1.getText().isBlank() || !textField2.getText().isBlank() || !textField3.getText().isBlank() || !textField4.getText().isBlank() || !textField5.getText().isBlank())
                    {
                        Student student = new Student(textField1.getText(), textField2.getText(), textField3.getText(), textField4.getText(), textField5.getText());
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
                    throwables.printStackTrace();
                }
            }
        });
        enrrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = (Student) comboBox1.getSelectedItem();
                Course course = (Course) comboBox2.getSelectedItem();
                Database database = new Database();

                if(!database.isEnrrolled(student, course) || database.hasPassedCourse(student))
                {
                    new Database().enrollStudent(student, course);
                    new Database().addScore(student, course);
                }

                refreshComboBox();
            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) comboBox3.getSelectedItem();
                String report = new Database().retrieveReport(student);
                textPane1.setText(report);
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showSaveDialog(new JFrame());
                File file = jFileChooser.getSelectedFile();
                if(file != null)
                {
                    try {
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(textPane1.getText());
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
                        database.addStudentList(studentList);
                        database.addCourseList(courseList);
                        database.addSubjectList(subjectList);
                        refreshComboBox();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (ParserConfigurationException parserConfigurationException) {
                        parserConfigurationException.printStackTrace();
                    } catch (SAXException saxException) {
                        saxException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 500);
        frame.setVisible(true);

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("import.xml", new XMLReader());
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
    private void refreshComboBox()
    {
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        comboBox3 = new JComboBox();

        ComboBoxModel studentModel = new DefaultComboBoxModel((new Database().retrieveStudentList().toArray()));
        ComboBoxModel courseModel = new DefaultComboBoxModel(new Database().retrieveCourseList().toArray());
        comboBox1.setModel(studentModel);
        comboBox2.setModel(courseModel);
        comboBox3.setModel(studentModel);
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        comboBox3 = new JComboBox();

        ComboBoxModel studentModel = new DefaultComboBoxModel((new Database().retrieveStudentList().toArray()));
        ComboBoxModel courseModel = new DefaultComboBoxModel(new Database().retrieveCourseList().toArray());
        comboBox1.setModel(studentModel);
        comboBox2.setModel(courseModel);
        comboBox3.setModel(studentModel);
    }
}
