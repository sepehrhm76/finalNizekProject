package org.example.view;

import org.example.Manager.UserManager;
import org.example.view.LoginPanel;

import javax.swing.*;

public class UiFrame extends JFrame {


    private static UiFrame instance = null;
    LoginPanel loginPanel = new LoginPanel();

    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }


    private UiFrame() {
        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
//        add(loginPanel);
        MainPanel mainPanel = new MainPanel();
        add(mainPanel);
    }
}
