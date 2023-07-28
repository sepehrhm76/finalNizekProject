package org.example.view;

import org.example.view.LoginPanel;

import javax.swing.*;

public class UiFrame extends JFrame {
    LoginPanel loginPanel = new LoginPanel();

    public UiFrame () {
        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        add(loginPanel);

    }
}
