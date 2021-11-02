package org.example;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private JPanel panel;
    private JTabbedPane tabbedPane1;
    private JPanel studentsTab;
    private JPanel enrollmentsTab;
    private JPanel reportsTab;
    private JPanel utilitiesTab;

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
