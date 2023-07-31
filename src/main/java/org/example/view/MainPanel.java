package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    JButton projects;
    JButton membersButton;
    private static MainPanel instance = null;

    private MainPanel(){
        setLayout(null);
        setVisible(true);
        setBackground(Color.darkGray);
        setBounds(0,0,300,1040);
        projectsBtn();
        membersButtonInMain();
    }

    private void projectsBtn() {
        projects = new JButton("projects");
        projects.setBounds(25, 100, 250, 45);
        projects.setBorder(null);
        projects.setForeground(Color.white);
        projects.setBackground(new Color(33, 51, 99));
        projects.setOpaque(true);
        projects.setVisible(true);
        add(projects);
    }

    public void membersButtonInMain() {
        membersButton = new JButton("Members");
        membersButton.setBounds(25, 170, 250, 45);
        membersButton.setBorder(null);
        membersButton.setForeground(Color.white);
        membersButton.setBackground(new Color(33, 51, 99));
        membersButton.setOpaque(true);
        add(membersButton);
    }

    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }
}
