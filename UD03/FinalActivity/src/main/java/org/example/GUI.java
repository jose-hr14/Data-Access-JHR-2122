package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
//.setModel(new DefaultComboBox(arrayList.toArray()=
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


    public GUI() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!textField1.getText().isBlank() || !textField2.getText().isBlank() || !textField3.getText().isBlank() || !textField4.getText().isBlank() || !textField5.getText().isBlank()) {
                        Student student = new Student(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()),
                                textField4.getText(), textField5.getText());

                        new Database().addStudent(student);
                        resultLabel.setText("Student saved correctly");
                    } else {
                        resultLabel.setText("One or more textbox is blank");
                    }

                } catch (NumberFormatException numberFormatException) {
                    resultLabel.setText("ID Card must be a number");
                }
            }
        });
        enrrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = (Student) comboBox1.getSelectedItem();
                Course course = (Course) comboBox2.getSelectedItem();

                new Database().enrollStudent(student, course);
                new Database().addScore(student);
                //select c.name, s.name, s2.score from enrollment inner join course c on enrollment.course = c.code inner join subjects s on c.code = s.courseid inner join scores s2 on enrollment.code = s2.enrollmentid
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
                jFileChooser.setName("a");
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file);
                    fileWriter.write(textPane1.getText());
                    fileWriter.close();
                } catch (IOException ex) {

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
