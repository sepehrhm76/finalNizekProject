package org.example.view;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    MainPanel mainPanel=MainPanel.getInstance();
    LoginPanel loginPanel=LoginPanel.getInstance();
    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        loginPanel.setBounds(0,0,getWidth(),getHeight());
        mainPanel.setBounds(0,0,getWidth(),getHeight());

        add(loginPanel);
        mainPanel.setVisible(false);
        add(mainPanel);
    }

    public void switchToMainPanel() {
        loginPanel.setVisible(true);
       mainPanel.setVisible(true);
        repaint();
    }
    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }
}
