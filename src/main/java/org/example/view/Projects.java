package org.example.view;

import javax.swing.*;
import java.awt.*;

public class Projects extends JPanel {
    private static Projects instance = null;
    
    public Projects(){
        setLayout(null);
        setVisible(false);
        setBounds(300,0,1140,1040);
        setBackground(Color.WHITE);
    }
    


    public static Projects getInstance() {
        if (instance == null)
            instance = new Projects();
        return instance;
    }
}
