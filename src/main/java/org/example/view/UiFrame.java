package org.example.view;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    Members members = Members.getInstance();
    Projects projects = Projects.getInstance();
    LoginPanel loginPanel = LoginPanel.getInstance();
    MainPanel mainPanel = MainPanel.getInstance();


    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        add(loginPanel);
        add(mainPanel);
        add(projects);
        add(members);
//        switchToMainPanel();

    }

    public void switchToMainPanel() {
            loginPanel.setVisible(false);
            mainPanel.setVisible(true);
            MainPanel.getInstance().membersButton.doClick();
            repaint();
    }
    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }
}
