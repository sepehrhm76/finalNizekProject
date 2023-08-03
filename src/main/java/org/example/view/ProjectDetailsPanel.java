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
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 16));
        titleLabel.setBounds(500,500,500,100);
        setVisible(false);
        add(titleLabel);
    }

    public void setUpData(Project project) {
        this.project = project;
        titleLabel.setText("Project Title:" + this.project.getName());
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 16));
        titleLabel.setBounds(500,500,500,100);
        Logger.getInstance().logDebug(project.toString());
    }

    public static ProjectDetailsPanel getInstance() {
        if (instance == null)
            instance = new ProjectDetailsPanel();
        return instance;
    }

}
