package org.example.view;

import org.example.Conroller.IssueController;
import org.example.Conroller.PermissionController;
import org.example.Conroller.UserController;
import org.example.Model.Issue;
import org.example.Model.Project;
import org.example.Model.User;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Project_allIssue extends JPanel implements TableModel, AddIssue.AddIssueListener {
    Project project;
    JButton addIssueBtn;
    IssueController issueController = new IssueController();
    public JTable issuesTable = new JTable();
    private JTextField searchField = new JTextField();

    public Project_allIssue(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(true);
        addIssueBtn();
        createTable();
    }

    public void addIssueBtn() {
        addIssueBtn = new JButton("Add Issue");
        addIssueBtn.setBounds(1020, 100, 120, 40);
        addIssueBtn.setBorder(null);
        addIssueBtn.setForeground(Color.white);
        addIssueBtn.setBackground(new Color(33, 51, 99));
        addIssueBtn.setOpaque(true);
        addIssueBtn.setVisible(PermissionController.addDeleteAndEditIssue());
        add(addIssueBtn);

        addIssueBtn.addActionListener(e -> {
            AddIssue addIssue = new AddIssue(this, addIssueBtn, null, this.project, null);

            searchField.requestFocus();
        });
    }

    public void createTable() {
        issuesTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(issuesTable);
        scrollPane.setBounds(20, 160, 1100, 840);
        add(scrollPane, BorderLayout.CENTER);

        JTableHeader tableHeader = issuesTable.getTableHeader();
        tableHeader.setFont(new Font("Arial Rounded", Font.BOLD, 12));
        tableHeader.setBackground(new Color(33, 51, 99));
        tableHeader.setForeground(Color.white);

        setColumnWidths();
        issuesTable.setRowSelectionAllowed(false);
        int count = 7;
        if(PermissionController.deleteAndEditProject()) {
            count = 9;
        }
        for (int i = 0; i <= count; i++) {
            issuesTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        if(PermissionController.deleteAndEditProject()) {
            issuesTable.getColumn("Edit").setCellRenderer(new Project_allIssue.ButtonRenderer("Edit"));
            issuesTable.getColumn("Edit").setCellEditor(new Project_allIssue.ButtonEditor("Edit", new JCheckBox(), rowIndex -> {
                Issue issue = issueController.getIssuesByProjectId(this.project.getId()).get(rowIndex);
                int userId = issue.getUser_id();
                UserController userController = UserController.getInstance();
                User user = userController.getUserById(userId);
                AddIssue editeIssueData = new AddIssue(this, addIssueBtn, issueController.getIssuesByProjectId(this.project.getId()).get(rowIndex), this.project, user);
            }));

            issuesTable.getColumn("Delete").setCellRenderer(new Members.ButtonRenderer("Delete"));
            issuesTable.getColumn("Delete").setCellEditor(new Members.ButtonEditor("Delete", new JCheckBox(), rowIndex -> {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this issue?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    Issue issueToDelete = issueController.getIssuesByProjectId(this.project.getId()).get(rowIndex);
                    issueController.removeIssue(issueToDelete);
                    issuesTable.setVisible(false);
                    issuesTable.setVisible(true);
                }
            }));
        }

        issuesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int row = issuesTable.rowAtPoint(e.getPoint());
                    int column = issuesTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 0) {
                        String columnTitle = issuesTable.getColumnName(column);
                        Object cellValue = issuesTable.getValueAt(row, column);
                        showDescriptionDialog(columnTitle, cellValue.toString());

                    }
                }
            }
        });
    }

    private void showDescriptionDialog(String columnTitle, String data) {
        JDialog descriptionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), columnTitle, true);
        descriptionDialog.setLayout(new BorderLayout());
        descriptionDialog.setSize(500, 200);
        descriptionDialog.setLocationRelativeTo(null);

        JTextArea descriptionTextArea = new JTextArea(data);
        descriptionTextArea.setBackground(Color.WHITE);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(descriptionTextArea);
        descriptionDialog.add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> descriptionDialog.dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        descriptionDialog.add(buttonPanel, BorderLayout.SOUTH);

        descriptionDialog.setVisible(true);
    }

    @Override
    public int getRowCount() {
        return issueController.getIssuesByProjectId(this.project.getId()).size();
    }

    @Override
    public int getColumnCount() {
        if (PermissionController.addDeleteAndEditIssue()) {
            return 11;
        } else {
            return 9;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Title";
            case 2:
                return "Description";
            case 3:
                return "Tags";
            case 4:
                return "Type";
            case 5:
                return "Priority";
            case 6:
                return "User Assign";
            case 7:
                return "State";
            case 8:
                return "Created Time";
            case 9:
                return "Edit";
            case 10:
                return "Delete";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Issue issue = issueController.getIssuesByProjectId(this.project.getId()).get(rowIndex);

        switch (columnIndex) {
            case 0:
                return issue.getId();
            case 1:
                return issue.getTitle();
            case 2:
                return issue.getDescription();
            case 3:
                return issue.getTag();
            case 4:
                return issue.getType();
            case 5:
                return issue.getPriority();
            case 6:
                int userId = issue.getUser_id();
                UserController userController = UserController.getInstance();
                User user = userController.getUserById(userId);

                if (user != null) {
                    return " (ID: " + user.getId() + ")" + user.getFirstName() + " " + user.getLastName();
                } else {
                    return "        -";
                }

            case 7:
                return issue.getState();
            case 8:
                String utcDateString = issue.getCDate();
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                ZonedDateTime utcDateTime = ZonedDateTime.parse(utcDateString, inputFormatter.withZone(ZoneId.of("UTC")));

                ZoneId localZone = ZoneId.systemDefault();
                ZonedDateTime localDateTime = utcDateTime.withZoneSameInstant(localZone);

                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedLocalDateTime = localDateTime.format(outputFormatter);

                return formattedLocalDateTime;

            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = issuesTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(180);
        columnModel.getColumn(7).setPreferredWidth(100);
        columnModel.getColumn(8).setPreferredWidth(120);
        if (PermissionController.addDeleteAndEditIssue()) {
            columnModel.getColumn(9).setPreferredWidth(70);
            columnModel.getColumn(10).setPreferredWidth(70);
        }
    }

    @Override
    public void onIssueCreatedOrEdited() {
        issuesTable.setVisible(false);
        issuesTable.setVisible(true);
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String title) {
            setOpaque(true);
            setText(title);
        }


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private static class ButtonEditor extends DefaultCellEditor {
        private final JButton button;
        private boolean isPushed;
        private int currentRow;
        String title;
        public ButtonEditor(String title, JCheckBox checkBox, Members.ButtonCallback callback) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
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

    private static class NonSelectableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
        }
    }

}
