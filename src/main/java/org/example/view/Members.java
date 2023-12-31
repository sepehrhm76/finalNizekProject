package org.example.view;

import org.example.Conroller.UserController;
import org.example.Model.User;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;


public class Members extends JPanel implements TableModel{

    private static Members instance = null;
    UserController userController = UserController.getInstance();
    public JTable userTable = new JTable();
    private JButton addUserBtn;

    private Members() {
        setLayout(null);
        setBounds(0,0,1140,1040);
        setBackground(Color.WHITE);
        addUserBtn();
        createTable();
        headTitle();

    }

    public void addUserBtn() {
        addUserBtn = new JButton("Add User");
        addUserBtn.setBounds(1320, 200, 120, 40);
        addUserBtn.setBorder(null);
        addUserBtn.setForeground(Color.white);
        addUserBtn.setBackground(new Color(33, 51, 99));
        addUserBtn.setOpaque(true);
        add(addUserBtn);

        addUserBtn.addActionListener(e -> {
            AddUser addUserObject = new AddUser(addUserBtn, null);
        });
    }

    public void headTitle() {
        JLabel memberHeadTitle = new JLabel("Members");
        memberHeadTitle.setBounds(600, -100, 1000, 500);
        memberHeadTitle.setForeground(new Color(33, 51, 99));
        memberHeadTitle.setFont(new Font("Arial Rounded", Font.BOLD, 100));
        add(memberHeadTitle);
    }

    public void createTable() {
        userTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(400, 300, 940, 450);
        add(scrollPane, BorderLayout.CENTER);

        JTableHeader tableHeader = userTable.getTableHeader();
        tableHeader.setFont(new Font("Arial Rounded", Font.BOLD, 12));
        tableHeader.setBackground(new Color(33, 51, 99));
        tableHeader.setForeground(Color.white);

        setColumnWidths();

        userTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 4; i++) {
            userTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        userTable.getColumn("Edit").setCellRenderer(new ButtonRenderer("Edit"));
        userTable.getColumn("Edit").setCellEditor(new ButtonEditor("Edit", new JCheckBox(), rowIndex -> {

            AddUser addUserObject = new AddUser(addUserBtn, userController.getAllUser().get(rowIndex));
        }));

        userTable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
        userTable.getColumn("Delete").setCellEditor(new ButtonEditor("Delete", new JCheckBox(), rowIndex -> {
            int option = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this member?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                User userToDelete = userController.getAllUser().get(rowIndex);
               userController.removeUser(userToDelete);
                userTable.setVisible(false);
                userTable.setVisible(true);
            }
        }));
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = userTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(1);
        columnModel.getColumn(2).setPreferredWidth(70);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(10);
        columnModel.getColumn(5).setPreferredWidth(10);
        columnModel.getColumn(6).setPreferredWidth(10);
    }

    public void refreshTableData() {
    }
    @Override
    public int getRowCount() {
        return userController.getAllUser().size();
    }
    @Override
    public int getColumnCount() {
        return 7;
    }
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "ID";
            case 1: return "First Name";
            case 2: return "Last Name";
            case 3: return "Email";
            case 4: return "Role";
            case 5: return "Edit";
            case 6: return "Delete";
            default : return null;
        }
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5 && columnIndex == 6) return JButton.class;
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 5;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        List<User> users = userController.getAllUser();
        switch (columnIndex) {
            case 0: return users.get(rowIndex).getId();
            case 1: return users.get(rowIndex).getFirstName();
            case 2: return users.get(rowIndex).getLastName();
            case 3: return users.get(rowIndex).getEmail();
            case 4: return users.get(rowIndex).getRole();
            case 5: return null;
            case 6: return null;
            default: return null;
        }
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
    @Override
    public void addTableModelListener(TableModelListener l) {
        refreshTableData();
    }
    @Override
    public void removeTableModelListener(TableModelListener l) {
        refreshTableData();
    }
    public static Members getInstance() {
        if (instance == null)
            instance = new Members();
        return instance;
    }
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String title) {
            setOpaque(true);
            setText(title);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private boolean isPushed;
        private int currentRow;
        private final String title;
        public ButtonEditor(String title, JCheckBox checkBox, ButtonCallback callback) {
            super(checkBox);
            button = new JButton();
            this.title = title;
            button.addActionListener(e -> {
                fireEditingStopped();
                callback.onClick(currentRow);
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

    interface ButtonCallback {
        void onClick(int rowIndex);
    }

    static class NonSelectableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
            return component;
        }
    }

    public static void reset() {
        instance = new Members();
    }
}

