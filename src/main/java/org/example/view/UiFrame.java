package org.example.view;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    Members members = Members.getInstance();
    LoginPanel loginPanel=LoginPanel.getInstance();
    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        loginPanel.setBounds(0,0,getWidth(),getHeight());
        members.setBounds(0,0,getWidth(),getHeight());

//        add(loginPanel);
        members.setVisible(true); //false
        add(members);
    }

    public void switchToMainPanel() {
        loginPanel.setVisible(true);//false
       members.setVisible(true);
        repaint();
    }
    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }
}
