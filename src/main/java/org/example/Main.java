package org.example;

import org.example.Database.SQLiteWrapper;
import org.example.Database.user.UserRepository;
import org.example.Model.User;
import org.example.Model.UserRole;
import org.example.view.UiFrame;

import javax.swing.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UiFrame.getInstance();
            }
        });

        System.out.println(userRepository.getAll());


    }
}