package org.example.view;

import org.example.Conroller.PermissionController;
import org.example.Conroller.UserController;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    Members members;
    Projects projects;
    LoginPanel loginPanel = LoginPanel.getInstance();
    MainPanel mainPanel;


    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 1040);
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);

        add(loginPanel);
//        switchToMainPanel();

    }

    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }

    public void switchToMainPanel() {

        mainPanel = MainPanel.getInstance();
        members = Members.getInstance();
        projects = Projects.getInstance();
        add(mainPanel);
        add(projects);
        add(members);

        loginPanel.setVisible(false);
        mainPanel.setVisible(true);
        projects.setVisible(true);
        if (PermissionController.showMembers()) {
            MainPanel.getInstance().membersButton.doClick();
        } else {
            MainPanel.getInstance().projectsButton.doClick();
        }
        repaint();
    }

    public void switchToLoginPage() {
        UserController.getInstance().setCurrentUser(null);
        if (mainPanel != null) {
            mainPanel.setVisible(false);
        }
        if (projects != null) {
            projects.setVisible(false);
        }
        if (members != null) {
            members.setVisible(false);
        }
        if (loginPanel != null) {
            loginPanel.setVisible(true);
        }
        repaint();
    }
}
