package org.example.view;

import org.example.Conroller.IssueController;
import org.example.Conroller.UserController;
import org.example.Model.Issue;
import org.example.Model.Project;
import org.example.Model.User;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.TableRowSorter;

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
        add(addIssueBtn);

        addIssueBtn.addActionListener(e -> {
            AddIssue addIssue = new AddIssue(this,addIssueBtn,null,this.project);

            searchField.requestFocus();
        });
    }

    public void createTable() {
        issuesTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(issuesTable);
        scrollPane.setBounds(20, 160, 1100, 840);
        add(scrollPane, BorderLayout.CENTER);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this);
        issuesTable.setRowSorter(sorter);

        searchField.setBounds(150, 120, 200, 30);
        add(searchField);

        // Add a DocumentListener to the search field to perform real-time filtering
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                performSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                performSearch();
            }
        });

        JTableHeader tableHeader = issuesTable.getTableHeader();
        tableHeader.setFont(new Font("Arial Rounded", Font.BOLD, 12));
        tableHeader.setBackground(new Color(33, 51, 99));
        tableHeader.setForeground(Color.white);

        setColumnWidths();
        issuesTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 9; i++) {
            issuesTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        issuesTable.getColumn("Edit").setCellRenderer(new Members.ButtonRenderer("Edit"));
        issuesTable.getColumn("Edit").setCellEditor(new Members.ButtonEditor("Edit", new JCheckBox(), rowIndex -> {

            AddIssue editeIssueData = new AddIssue(this, addIssueBtn, issueController.getIssuesByProjectId(this.project.getId()).get(rowIndex),this.project);
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

    private void performSearch() {
        String searchText = searchField.getText();
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) issuesTable.getRowSorter();

        // Set the filter based on the search text
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText)); // (?i) for case-insensitive search
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
        return 10;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "ID";
            case 1 -> "Title";
            case 2 -> "Description";
            case 3 -> "Tags";
            case 4 -> "Type";
            case 5 -> "Priority";
            case 6 -> "User Assign";
            case 7 -> "Created Time";
            case 8 -> "Edit";
            case 9 -> "Delete";
            default -> null;
        };
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
                UserController userController = new UserController();
                User user = userController.getUserById(userId);

                if (user != null) {
                    return " (ID: " + user.getId() + ")" +user.getFirstName() + " " + user.getLastName();
                } else {
                    return "        -";
                }

            case 7:
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
        columnModel.getColumn(7).setPreferredWidth(120);
        columnModel.getColumn(8).setPreferredWidth(70);
        columnModel.getColumn(9).setPreferredWidth(70);
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
