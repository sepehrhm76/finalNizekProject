package org.example.view;

import org.example.Database.user.UserRepository;
import org.example.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel implements TableModel{

    private static MainPanel instance = null;
    UserRepository userRepository = new UserRepository();
    public JTable userTable = new JTable();
    private JButton addUser;

    private MainPanel() {
        setLayout(null);
        setVisible(true);
        setBackground(Color.WHITE);
        addUserBtn();
        createTable();
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
    public void createTable() {
        userTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(300, 70, 800, 500);
        add(scrollPane, BorderLayout.CENTER);
        setColumnWidths();
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = userTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(70);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(10);
    }

    public void refreshTableData() {
    }
    @Override
    public int getRowCount() {
        return userRepository.getAll().size();
    }
    @Override
    public int getColumnCount() {
        return 5;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "ID";
            case 1 -> "First Name";
            case 2 -> "Last Name";
            case 3 -> "Email";
            default -> "Role";
        };
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> userRepository.getAll().get(rowIndex).getId();
            case 1 -> userRepository.getAll().get(rowIndex).getFirstName();
            case 2 -> userRepository.getAll().get(rowIndex).getLastName();
            case 3 -> userRepository.getAll().get(rowIndex).getEmail();
            default -> userRepository.getAll().get(rowIndex).getRole();
        };
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        List<User> users = userRepository.getAll();

    }
    @Override
    public void addTableModelListener(TableModelListener l) {
        refreshTableData();
    }
    @Override
    public void removeTableModelListener(TableModelListener l) {
        refreshTableData();
    }

    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }

}

