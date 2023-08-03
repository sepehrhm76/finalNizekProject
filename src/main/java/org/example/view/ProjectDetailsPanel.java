package org.example.view;

import org.example.Log.Logger;
import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectDetailsPanel extends JPanel {
    private static ProjectDetailsPanel instance = null;
    private Project project;
    JLabel titleLabel;
    private ProjectDetailsPanel() {
        setLayout(null);
        setBounds(300,0,1140,1040);
        setVisible(false);
        titleLabel = new JLabel();
        add(titleLabel);
    }

    public void setUpData(Project project) {
        this.project = project;
        titleLabel.setText(this.project.getName() + " Project");
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 30));
        titleLabel.setBounds(450,60,500,100);
    }

    public static ProjectDetailsPanel getInstance() {
        if (instance == null)
            instance = new ProjectDetailsPanel();
        return instance;
    }
}
