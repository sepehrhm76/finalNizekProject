package org.example.view;

import org.example.Conroller.IssueController;
import org.example.Model.Issue;
import org.example.Model.Project;
import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Project_allIssue extends JPanel implements TableModel {
    Project project;
    JButton addIssue;
    IssueController issueController = new IssueController();
    public JTable issuesTable = new JTable();

    public Project_allIssue(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(false);
        buttons();
        addIssueBtn();
    }

    public void addIssueBtn() {
        addIssue = new JButton("Add Issue");
        addIssue.setBounds(1320, 50, 120, 40);
        addIssue.setBorder(null);
        addIssue.setForeground(Color.white);
        addIssue.setBackground(new Color(33, 51, 99));
        addIssue.setOpaque(true);
        add(addIssue);

        addIssue.addActionListener(e -> {

        });

        createTable();
    }

    public void createTable() {
        issuesTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(issuesTable);
        scrollPane.setBounds(300, 100, 1140, 940);
        add(scrollPane, BorderLayout.CENTER);
        setColumnWidths();
        issuesTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 9; i++) {
            issuesTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        issuesTable.getColumn("Edit").setCellRenderer(new Members.ButtonRenderer("Edit"));
        issuesTable.getColumn("Edit").setCellEditor(new Members.ButtonEditor("Edit", new JCheckBox(), rowIndex -> {

//            AddUser addUserObject = new AddUser(addUserBtn, userController.getAllUser().get(rowIndex));
        }));

        issuesTable.getColumn("Delete").setCellRenderer(new Members.ButtonRenderer("Delete"));
        issuesTable.getColumn("Delete").setCellEditor(new Members.ButtonEditor("Delete", new JCheckBox(), rowIndex -> {
            int option = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this member?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                Issue issueToDelete = issueController.getAllIssues().get(rowIndex);
                issueController.removeIssue(issueToDelete);
                issuesTable.setVisible(false);
                issuesTable.setVisible(true);
            }
        }));

        issuesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check for double-click (left mouse button)
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int row = issuesTable.rowAtPoint(e.getPoint());
                    int column = issuesTable.columnAtPoint(e.getPoint());
                    // Only execute if the double click happened on a valid row and column
                    if (row >= 0 && column >= 0) {
                        // Get the value of the cell and print it
                        Object cellValue = issuesTable.getValueAt(row, column);

                    }
                }
            }
        });
    }

    private void showDescriptionDialog(String description) {
        JDialog descriptionDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Description", true);
        descriptionDialog.setLayout(new BorderLayout());
        descriptionDialog.setSize(500, 200);
        descriptionDialog.setLocationRelativeTo(null);

        JTextArea descriptionTextArea = new JTextArea(description);
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
        return issueController.getAllIssues().size();
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
        return switch (columnIndex) {
            case 0 -> issueController.getAllIssues().get(rowIndex).getId();
            case 1 -> issueController.getAllIssues().get(rowIndex).getTitle();
            case 2 -> issueController.getAllIssues().get(rowIndex).getDescription();
            case 3 -> issueController.getAllIssues().get(rowIndex).getTag();
            case 4 -> issueController.getAllIssues().get(rowIndex).getType();
            case 5 -> issueController.getAllIssues().get(rowIndex).getPriority();
            case 6 -> issueController.getAllIssues().get(rowIndex).getUser_id();
            case 7 -> issueController.getAllIssues().get(rowIndex).getCDate();
            default -> null;
        };
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

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(130);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(5).setPreferredWidth(50);
        columnModel.getColumn(6).setPreferredWidth(50);
        columnModel.getColumn(7).setPreferredWidth(50);
        columnModel.getColumn(8).setPreferredWidth(50);
        columnModel.getColumn(9).setPreferredWidth(50);
    }

    public void buttons() {
        JButton projectDetailsButton = new JButton("Project Details");
        projectDetailsButton.setBounds(320, 50, 150, 40);
        projectDetailsButton.addActionListener(e -> {
            setVisible(false);
            ProjectDetailsPanel.getInstance().setVisible(true);
            ProjectDetailsPanel.getInstance().setUpData(this.project);
        });
        add(projectDetailsButton);

        JButton boardsButton = new JButton("Boards");
        boardsButton.setBounds(490, 50, 150, 40);
        boardsButton.addActionListener(e -> {
            setVisible(false);
            Project_Board projectBoard = new Project_Board(this.project);
            UiFrame.getInstance().add(projectBoard);
            projectBoard.setVisible(true);
        });
        add(boardsButton);

        JButton allIssuesButton = new JButton("All Issues");
        allIssuesButton.setBounds(660, 50, 150, 40);
        add(allIssuesButton);

        JButton reportButton = new JButton("Reports");
        reportButton.setBounds(830, 50, 150, 40);
        reportButton.addActionListener(e -> {
            setVisible(false);
            Project_Report projectReport = new Project_Report(this.project);
            UiFrame.getInstance().add(projectReport);
            projectReport.setVisible(true);
        });
        add(reportButton);
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
