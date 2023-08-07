package org.example.view;

import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    private static MainPanel instance = null;
    Members members = Members.getInstance();
    Projects projects = Projects.getInstance();
    ProjectDetailsPanel projectDetailsPanel = ProjectDetailsPanel.getInstance();
    JButton projectsButton;
    JButton membersButton;


    private MainPanel(){
        setLayout(null);
        setVisible(false);
        setBackground(Color.darkGray);
        setBounds(0,0,300,1040);
        projectsBtn();
        membersButtonInMain();
    }
    private void projectsBtn() {
        projectsButton = new JButton("projects");
        projectsButton.setBounds(25, 100, 250, 45);
        projectsButton.setBorder(null);
        projectsButton.setForeground(Color.white);
        projectsButton.setBackground(new Color(33, 51, 99));
        projectsButton.setOpaque(true);
        projectsButton.setVisible(true);
        add(projectsButton);

        projectsButton.addActionListener(e -> {

            members.setVisible(false);
            projects.setVisible(true);
            projectDetailsPanel.setVisible(false);

        });
    }
    public void membersButtonInMain() {
        membersButton = new JButton("Members");
        membersButton.setBounds(25, 170, 250, 45);
        membersButton.setBorder(null);
        membersButton.setForeground(Color.white);
        membersButton.setBackground(new Color(33, 51, 99));
        membersButton.setOpaque(true);
        add(membersButton);
        membersButton.addActionListener(e -> {
            projects.setVisible(false);
            members.setVisible(true);
            projectDetailsPanel.setVisible(false);

        });
    }
    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }
}
