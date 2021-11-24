package org.final_activity;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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

/**
 * This class controls everything related to the graphical user interface
 * @author José Hernández Riquelme
 */
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
    private JLabel printResult;

    /**
     * The main method that sets de form and shows it to the user.
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(320, 300);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    /**
     * Graphical user interface constructor. It holds every component listener, managing the whole GUI behaviour.
     */
    public GUI() {
        refreshComboBoxes();
        /**
         * Listener of the add student button. It creates a new student and saves them in the database
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!studentFirstNameTextField.getText().isBlank() || !studentLastNameTextField.getText().isBlank()
                            || !studentIDTextField.getText().isBlank()) {
                        Student student = new Student(studentFirstNameTextField.getText(),
                                studentLastNameTextField.getText(),
                                studentIDTextField.getText(), studentEmailTextField.getText(),
                                studentPhoneTextField.getText());
                        new Database().addStudent(student);
                        resultLabel.setText("Student saved correctly");
                        refreshStudentComboBox();
                    } else {
                        //resultLabel.setText("One or more textbox is blank");
                        JOptionPane.showMessageDialog(new JFrame(), "One or more mandatory textboxes are blank " +
                                "(first name, last name and ID)", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch (SQLException throwables) {
                    //resultLabel.setText("Student already exits");
                    JOptionPane.showMessageDialog(new JFrame(), throwables.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        /**
         * Enrollment button listener. It takes a student id and a course code, and saves them
         * in the enrollment table of the database. Furthermore, saves all the subjects of the selected course
         * within the scores table, with the enrollment id and the and the score of each subject, that will be 0
         * by default.
         */
        enrrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) studentComboBox.getSelectedItem();
                Course course = (Course) courseComboBox.getSelectedItem();
                Database database = new Database();

                if (database.isEnrrolled(student, course)) {
                    //enrollmentLabel.setText("Student is already enrolled in this course");
                    JOptionPane.showMessageDialog(new JFrame(), "Student is already enrolled in this course",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!database.hasPassedCourses(student)) {
                    //enrollmentLabel.setText("Student hasn't passed previous courses");
                    JOptionPane.showMessageDialog(new JFrame(), "Student hasn't passed previous courses",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    database.enrollStudent(student, course);
                    database.addScore(student, course);
                    enrollmentLabel.setText("Student enrolled successfully");
                }
                refreshEnrrolledStudentComboBox();
            }
        });
        /**
         * Student report comboBox listener. When a enrolled student is selected, it prints their result
         * into de textpane.
         */
        studentReportComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshReportsPane();
            }
        });
        /**
         * Scores print button listener. When clicked, it opens a filechooser that allows the user to save
         * the selected student score into a txt file.
         */
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = null;
                Student student = (Student) studentReportComboBox.getSelectedItem();
                if (!reportsTextPane.getText().isBlank()) {
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setSelectedFile(new File(student.firstName + "_" + student.getLastName()
                            + "_Marks.txt"));
                    jFileChooser.setFileFilter(new FileNameExtensionFilter("txt file", "txt"));
                    int dialogResult = jFileChooser.showSaveDialog(new JFrame());
                    file = jFileChooser.getSelectedFile();

                    if (dialogResult == JFileChooser.APPROVE_OPTION) {
                        try {
                            FileWriter fileWriter = new FileWriter(file);
                            fileWriter.write("-- " + student.firstName + " " + student.lastName + " Marks -- \r\n");
                            fileWriter.write(reportsTextPane.getText());
                            fileWriter.close();
                            printResult.setText("Student saved successfully");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else
                        printResult.setText("");
                }
            }
        });
        /**
         * Import XML button listener. It reads a xml file and saves the students, courses or subjects written on it.
         * The importing operation will be transactional, this means that if the importing of one or more element fails
         * the whole operation will be aborted.
         */
        importXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("XML file", "xml"));
                int dialogResult = jFileChooser.showOpenDialog(new JFrame());
                File file = jFileChooser.getSelectedFile();
                if (dialogResult == JFileChooser.APPROVE_OPTION) {
                    try {
                        SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                        XMLReader xmlReader = new XMLReader();
                        saxParser.parse(file, xmlReader);
                        Database database = new Database();
                        ArrayList<Student> studentList = xmlReader.getStudentList();
                        ArrayList<Course> courseList = xmlReader.getCourseList();
                        ArrayList<Subject> subjectList = xmlReader.getSubjectList();
                        database.importXML(studentList, courseList, subjectList);
                        refreshStudentComboBox();
                        xmlImportLabel.setText("Data imported successfully");
                        refreshStudentComboBox();
                        refreshCourseComboBox();
                    } catch (ParserConfigurationException parserConfigurationException) {
                        JOptionPane.showMessageDialog(new JFrame(), parserConfigurationException.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SAXException saxException) {
                        JOptionPane.showMessageDialog(new JFrame(), saxException.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
/*                        if (!file.getName().contains(".xml")) {
                            xmlImportLabel.setText("The opened file is not a xml file");
                            JOptionPane.showMessageDialog(new JFrame(), "The opened file is not a xml file", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            xmlImportLabel.setText("The opened xml file doesn't match the required format or is empty");
                            JOptionPane.showMessageDialog(new JFrame(), "The opened xml file doesn't match the required format or is empty", "Error", JOptionPane.ERROR_MESSAGE);
                        }*/
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException throwable) {
                        JOptionPane.showMessageDialog(new JFrame(), throwable.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
/*
                        xmlImportLabel.setText("Data couldn't be imported, aborting transaction");

                        if(throwable.getMessage().contains("ERROR: llave duplicada viola restricción de unicidad «student_pkey»"))
                            JOptionPane.showMessageDialog(new JFrame(), "One or more students already exits", "Error", JOptionPane.ERROR_MESSAGE);
                            //xmlImportLabel.setText("One or more students already exits");
                        if(throwable.getMessage().contains("ERROR: llave duplicada viola restricción de unicidad «course_pkey»"))
                            JOptionPane.showMessageDialog(new JFrame(), "One or more courses already exits", "Error", JOptionPane.ERROR_MESSAGE);
                            //xmlImportLabel.setText("One or more courses already exits");
                        if(throwable.getMessage().contains("ERROR: llave duplicada viola restricción de unicidad «subjects_pkey»"))
                            JOptionPane.showMessageDialog(new JFrame(), "One or more subjects already exits", "Error", JOptionPane.ERROR_MESSAGE);
                            //xmlImportLabel.setText("One or more subjects already exits");
                        if(throwable.getMessage().contains("ERROR: inserción o actualización en la tabla «subjects» viola la llave foránea «fk_courseid»"))
                            JOptionPane.showMessageDialog(new JFrame(), "One or more subjects are from a course that doest not exits", "Error", JOptionPane.ERROR_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(new JFrame(), "Data couldn't be imported, aborting transaction", "Error", JOptionPane.ERROR_MESSAGE);
                            //xmlImportLabel.setText("One or more subjects are from a course that doest not exits");
*/
                    }
                }
            }
        });
    }
    /**
     * This function refreshes the reports' pane with the selected index from the enrolled students comboBox.
     */
    public void refreshReportsPane() {
        Student student = (Student) studentReportComboBox.getSelectedItem();
        String report = new Database().retrieveReport(student);
        reportsTextPane.setText(report);
    }
    /**
     * This function refreshes the student comboBox overwriting its current comboBox model
     */
    private void refreshStudentComboBox() {
        ComboBoxModel studentModel = new DefaultComboBoxModel((new Database().retrieveStudentList().toArray()));
        studentComboBox.setModel(studentModel);
    }
    /**
     * This function refreshes the course comboBox overwriting its current comboBox model
     */
    private void refreshCourseComboBox() {
        ComboBoxModel courseModel = new DefaultComboBoxModel(new Database().retrieveCourseList().toArray());
        courseComboBox.setModel(courseModel);
    }

    /**
     * This function refreshes the enrolled students comboBox overwriting its current model
     */
    private void refreshEnrrolledStudentComboBox() {
        ComboBoxModel enrrolledStudentsModel = new DefaultComboBoxModel(new Database().retrieveEnrolledStudentsList().toArray());
        studentReportComboBox.setModel(enrrolledStudentsModel);
    }
    /**
     * This function refreshes all the comboBoxes calling their respective functions
     */
    private void refreshComboBoxes() {
        refreshStudentComboBox();
        refreshCourseComboBox();
        refreshEnrrolledStudentComboBox();
    }
}
