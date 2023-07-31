package org.example.view;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    Members members = Members.getInstance();
    Projects projects = Projects.getInstance();
    LoginPanel loginPanel=LoginPanel.getInstance();
    boolean isLogin;
    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        isLogin = true;

        loginPanel.setBounds(0,0,getWidth(),getHeight());
        members.setBounds(0,0,getWidth(),getHeight());

        add(MainPanel.getInstance());
        MainPanel.getInstance().setVisible(true);
//        add(loginPanel);
        add(members);
        members.setVisible(true);

//
    }

    public void switchToMainPanel() {
            loginPanel.setVisible(false);
            MainPanel.getInstance().setVisible(true);
            members.setVisible(true);
            repaint();

    }
    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }
}
