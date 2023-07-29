package org.example.view;

import javax.swing.*;

public class UiFrame extends JFrame {

    private static UiFrame instance = null;
    private UiFrame() {

        setTitle("Sepiaj PMT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440,1040);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        add(LoginPanel.getInstance());
    }
    public static UiFrame getInstance() {
        if (instance == null)
            instance = new UiFrame();
        return instance;
    }
}
