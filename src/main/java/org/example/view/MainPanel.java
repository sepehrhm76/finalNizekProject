package org.example.view;

import org.example.Database.user.UserRepository;
import org.example.Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }

    //    public void createTable() {
//        String[] columnNames = {"First Name", "Last Name", "Email", "Role"};
//        Object[][] data = getUserDataFromDatabase();
//        tableModel = new DefaultTableModel(data, columnNames);
//        userTable = new JTable(tableModel);
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
//        userTable.setRowSorter(sorter);
//        JScrollPane scrollPane = new JScrollPane(userTable);
//        scrollPane.setBounds(440, 50, 1000, 990);
//        add(scrollPane, BorderLayout.CENTER);
//    }
    public void createTable() {
        String[] columnNames = {"First Name", "Last Name", "Email", "Role", "Edit", "Remove"};
        Object[][] data = getUserDataFromDatabase();
        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex >= 4 ? JButton.class : Object.class;
            }
        };

        userTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 4;
            }
        };

        userTable.getColumn("Edit").setCellRenderer(new ButtonCellRenderer("Edit"));
        userTable.getColumn("Edit").setCellEditor(new ButtonCellEditor("Edit"));

        userTable.getColumn("Remove").setCellRenderer(new ButtonCellRenderer("Remove"));
        userTable.getColumn("Remove").setCellEditor(new ButtonCellEditor("Remove"));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        userTable.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(440, 50, 1000, 990);
        add(scrollPane, BorderLayout.CENTER);
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

    private Object[][] getUserDataFromDatabase() {
        UserRepository userRepository = new UserRepository();
        List<User> userList = userRepository.getAll();
        Object[][] data = new Object[userList.size()][6];
        int row = 0;
        for (User user : userList) {
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

    private static class ButtonCellRenderer extends JButton implements TableCellRenderer {

        public ButtonCellRenderer(String text) {
            super(text);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ButtonCellEditor extends DefaultCellEditor {

        private JButton button;
        private String actionText;

        public ButtonCellEditor(String actionText) {
            super(new JCheckBox());
            this.actionText = actionText;
            this.button = new JButton(actionText);
            this.button.setOpaque(true);
            this.button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int selectedRow = userTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Handle button click here based on the actionText
                        // For example:
                        if (actionText.equals("Edit")) {
                            Object firstName = tableModel.getValueAt(selectedRow, 0);
                            Object lastName = tableModel.getValueAt(selectedRow, 1);
                            Object email = tableModel.getValueAt(selectedRow, 2);
                            Object role = tableModel.getValueAt(selectedRow, 3);

                            // Show a dialog with the data from the selected row
                            String message = "Edit User:\nFirst Name: " + firstName + "\n"
                                    + "Last Name: " + lastName + "\n"
                                    + "Email: " + email + "\n"
                                    + "Role: " + role;
                            JOptionPane.showMessageDialog(null, message, "Edit User", JOptionPane.INFORMATION_MESSAGE);
                        } else if (actionText.equals("Remove")) {
                            // Show a confirmation dialog before removing
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to remove this user?", "Confirm Removal", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // Perform the removal operation here
                                tableModel.removeRow(selectedRow);
                            }
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return this.button;
        }
    }
}