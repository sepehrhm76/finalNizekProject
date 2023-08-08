package org.example.view;

import org.example.Model.Project;

import javax.swing.*;

public class Project_Board extends JPanel {
    Project project;

    public Project_Board(Project project) {
        System.out.println("BOARD");
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(true);
        JLabel hi=new JLabel("HIIII");
        hi.setBounds(50,50,100,1000);
        add(hi);

        JButton jButton = new JButton("board");
        jButton.setBounds(500, 500, 100, 100);
        add(jButton);
    }
}
