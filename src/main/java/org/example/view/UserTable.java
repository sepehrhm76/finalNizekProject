package org.example.view;
import org.example.Model.User;
import java.awt.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import javax.swing.*;

public class UserTable extends JPanel {


    MainPanel mainPanel =MainPanel.getInstance();
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserTable(List<User> userList) {
        setLayout(new BorderLayout());

        String[] columnNames = {"First Name", "Last Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);

        for (User user : userList) {
            tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()});
        }

        userTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

    }
}
