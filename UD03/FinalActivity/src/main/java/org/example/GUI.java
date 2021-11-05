package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(320, 300);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox();
        for (Student student : new Database().retrieveStudentList()) {
            comboBox1.addItem(student);
        }
        comboBox2 = new JComboBox();
        for (Course course : new Database().retrieveCourseList()) {
            comboBox2.addItem(course);
        }
    }
}
