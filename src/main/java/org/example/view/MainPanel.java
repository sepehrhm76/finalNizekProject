package org.example.view;

import org.example.Database.user.UserRepository;
import org.example.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            AddUser addUserObject = new AddUser(addUser, null);
        });
    }
    public void createTable() {
        userTable.setModel(this);

        userTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());

        userTable.getColumn("Edit").setCellEditor(new ButtonEditor("Edit", new JCheckBox(), new ButtonCallback() {
            @Override
            public void onClick(int rowIndex) {
                // Handle delete action here
//                System.out.println("letsss edittttt  row at index: " + rowIndex);
                // Implement your deletion logic or any other action here.

                AddUser addUserObject = new AddUser(addUser, userRepository.getAll().get(rowIndex));
            }
        }));

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
        columnModel.getColumn(5).setPreferredWidth(10);
    }

    public void refreshTableData() {
    }
    @Override
    public int getRowCount() {
        return userRepository.getAll().size();
    }
    @Override
    public int getColumnCount() {
        return 6;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "ID";
            case 1 -> "First Name";
            case 2 -> "Last Name";
            case 3 -> "Email";
            case 4 -> "Role";
            case 5 -> "Edit";
            default -> null;
        };
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5) return JButton.class;
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5; // Allow editing only for the "Edit" button column
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> userRepository.getAll().get(rowIndex).getId();
            case 1 -> userRepository.getAll().get(rowIndex).getFirstName();
            case 2 -> userRepository.getAll().get(rowIndex).getLastName();
            case 3 -> userRepository.getAll().get(rowIndex).getEmail();
            case 4 -> userRepository.getAll().get(rowIndex).getRole();
            case 5 -> null;
            default -> null;
        };
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        List<User> users = userRepository.getAll();
//
//        if (columnIndex == 5) {
//            System.out.println("hoyyy");
//            // Perform the action when the "Edit" button is clicked
//            // You can access the data for the selected row using rowIndex
//            System.out.println("Edit button clicked for row " + rowIndex);
//        }
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

    // Custom cell renderer for the "Edit" button column
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Edit");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Custom cell editor for the delete button
    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private final ButtonCallback callback;
        private boolean isPushed;
        private int currentRow;
        private String title;
        public ButtonEditor(String title, JCheckBox checkBox, ButtonCallback callback) {
            super(checkBox);
            this.callback = callback;
            button = new JButton();
            button.setOpaque(true);
            this.title = title;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    callback.onClick(currentRow);
                }
            });


        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }

            button.setText(title);
            isPushed = true;
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Button was clicked, but we don't need to do anything here.
                // The action is handled in the actionPerformed method.
            }
            isPushed = false;
            return title;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    // Interface for the delete button callback
    interface ButtonCallback {
        void onClick(int rowIndex);
    }
}

