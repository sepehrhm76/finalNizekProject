package org.example.view;

import org.example.Manager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    private static MainPanel instance = null;
    private final JButton addUser;
    private AddUser addUser1;
    private MainPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setVisible(true);

        addUser = new JButton("Add User");
        addUser.setBounds(100, 100, 120, 43);
        addUser.setBorder(null);
        addUser.setForeground(Color.white);
        addUser.setBackground(new Color(33, 51, 99));
        addUser.setOpaque(true);
        add(addUser);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser1 = new AddUser(addUser);
            }

        });
    }
    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }

}
