package org.example.view;

import org.example.Database.user.UserRepository;
import org.example.Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel {

    private static MainPanel instance = null;
    private JButton addUser;
    private JTable userTable;
    private DefaultTableModel tableModel;

    private MainPanel() {
        setLayout(null);
        setVisible(true);
        setBackground(Color.WHITE);
        addUserBtn();
        createTable();
    }

    public void createTable() {
        String[] columnNames = {"First Name", "Last Name", "Email", "Role"};
        Object[][] data = getUserDataFromDatabase();
        tableModel = new DefaultTableModel(data, columnNames);
        userTable = new JTable(tableModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        userTable.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(440, 50, 1000, 990);
        add(scrollPane, BorderLayout.CENTER);
    }

    private Object[][] getUserDataFromDatabase() {
        UserRepository userRepository = new UserRepository();
        Object[][] data = new Object[userRepository.getAll().size()][4];
        int row = 0;
        for (User user : userRepository.getAll()) {
            data[row][0] = user.getFirstName();
            data[row][1] = user.getLastName();
            data[row][2] = user.getEmail();
            data[row][3] = user.getRole();
            row++;
        }
        return data;
    }

    public void updateTable(List<User> userList) {
        tableModel.setRowCount(0);
        for (User user : userList) {
            tableModel.addRow(new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole()});
        }
    }

    public void refreshTableData() {
        UserRepository userRepository = new UserRepository();
        List<User> userList = userRepository.getAll();
        updateTable(userList);
    }

    private void addUserBtn() {
        addUser = new JButton("Add User");
        addUser.setBounds(0, 0, 120, 43);
        addUser.setBorder(null);
        addUser.setForeground(Color.white);
        addUser.setBackground(new Color(33, 51, 99));
        addUser.setOpaque(true);
        add(addUser);

        addUser.addActionListener(e -> {
            AddUser addUserObject = new AddUser(addUser);
        });
    }

    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }
}





