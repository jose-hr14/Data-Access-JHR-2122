package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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

    public GUI() {

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!textField1.getText().isBlank() || !textField2.getText().isBlank() || !textField3.getText().isBlank() || !textField4.getText().isBlank() || !textField5.getText().isBlank())
                    {
                        Student student = new Student(textField1.getText(), textField2.getText(), Integer.parseInt(textField3.getText()),
                                textField4.getText(), textField5.getText());

                        new Database().addStudent(student);
                        resultLabel.setText("Student saved correctly");
                    }
                    else {
                        resultLabel.setText("One or more textbox is blank");
                    }

                }
                catch (NumberFormatException numberFormatException)
                {
                    resultLabel.setText("ID Card must be a number");
                }
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) comboBox1.getItemAt(comboBox1.getSelectedIndex());
            }
        });
        enrrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = (Student) comboBox1.getSelectedItem();
                Course course = (Course) comboBox2.getItemAt(comboBox2.getSelectedIndex());

                new Database().enrollStudent(student, course);
                String cosas = (String) comboBox3.getSelectedItem();
            }
        });
        comboBox1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    comboBox1 = new Database().chargeStudentComboBox(comboBox1);
                } catch (SQLException ex) {
                    ex.printStackTrace();
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
        frame.setSize(320, 300);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        try {
            comboBox1 = new JComboBox();
            comboBox1.setModel(new DefaultComboBoxModel());
            comboBox1 = new Database().chargeStudentComboBox(comboBox1);

            comboBox2 = new JComboBox();
            comboBox2.setModel(new DefaultComboBoxModel());
            comboBox2 = new Database().chargeCourseComboBox(comboBox2);

            comboBox3 = new JComboBox();
            comboBox3.addItem("Hola");
            comboBox3.addItem("Adios");
            comboBox3.addItem("Ey");



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
