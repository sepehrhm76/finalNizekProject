package org.example.view;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private static HeaderPanel instance = null;

    public HeaderPanel() {
        setLayout(null);
        setVisible(false);
        setBackground(Color.gray);
        setBounds(300,0,1140,40);
    }

    public static HeaderPanel getInstance() {
        if (instance == null)
            instance = new HeaderPanel();
        return instance;
    }
}
