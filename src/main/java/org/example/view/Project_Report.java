package org.example.view;

import org.example.Model.Project;

import javax.swing.*;

public class Project_Report extends JPanel {
    Project project;

    public Project_Report(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(true);
        JButton jButton = new JButton("report");
        jButton.setBounds(500,500,100,100);
        add(jButton);
    }
}
