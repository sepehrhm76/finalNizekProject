package org.example.view;

import org.example.Conroller.ProjectController;
import org.example.Log.Logger;
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

public class Projects extends JPanel implements TableModel {
    static Projects instance = null;
    JTable projectTable = new JTable();
    JButton addProject;
    ProjectController projectController = new ProjectController();

    public Projects(){
        setLayout(null);
        setVisible(false);
        setBounds(300,0,1140,1040);
        setBackground(Color.cyan);
        createTable();
        addProjectBtn();
    }
    public void addProjectBtn() {
        addProject = new JButton("Add Project");
        addProject.setBounds(1020, 30, 120, 40);
        addProject.setBorder(null);
        addProject.setForeground(Color.white);
        addProject.setBackground(new Color(33, 51, 99));
        addProject.setOpaque(true);
        add(addProject);
        addProject.addActionListener(e -> {
            AddProject addProjectObject = new AddProject(addProject, null);
        });

    }
    public void createTable() {
        projectTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setBounds(100, 300, 940, 450);
        add(scrollPane, BorderLayout.CENTER);
        setColumnWidths();
        projectTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 4; i++) {
            projectTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        projectTable.getColumn("Edit").setCellRenderer(new ButtonRenderer("Edit"));
        projectTable.getColumn("Edit").setCellEditor(new Projects.ButtonEditor("Edit", new JCheckBox(), rowIndex -> {

            AddProject addProjectObject = new AddProject(addProject, projectController.getAllProject().get(rowIndex));
        }));

            projectTable.getColumn("Delete").setCellRenderer(new ButtonRenderer("Delete"));
            projectTable.getColumn("Delete").setCellEditor(new Projects.ButtonEditor("Delete", new JCheckBox(), rowIndex -> {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this project?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION
                );
                if (option == JOptionPane.YES_OPTION) {
                    Project projectToDelete = projectController.getAllProject().get(rowIndex);
                    projectController.removeProject(projectToDelete);
                    projectTable.setVisible(false);
                    projectTable.setVisible(true);
                }
            }));

            projectTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        int row = projectTable.rowAtPoint(e.getPoint());
                        int column = projectTable.columnAtPoint(e.getPoint());

                        if (row >= 0) {
                            if (column >= 0 && column <= 1) {
                                Project selectedProject = projectController.getAllProject().get(row);
                                ProjectDetailsPanel projectDetailsPanel = new ProjectDetailsPanel(selectedProject);
                                projectDetailsPanel.setVisible(true);
                                setVisible(false);

                            } else {
                                String description = (String) projectTable.getValueAt(row, 2);
                                showDescriptionDialog(description);
                            }
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
        return projectController.getAllProject().size();
    }
    @Override
    public int getColumnCount() {
        return 5;
    }
    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "ID";
            case 1 -> "Title";
            case 2 -> "Description";
            case 3 -> "Edit";
            case 4 -> "Delete";
            default -> null;
        };
    }
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 3;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> projectController.getAllProject().get(rowIndex).getId();
            case 1 -> projectController.getAllProject().get(rowIndex).getName();
            case 2 -> projectController.getAllProject().get(rowIndex).getDescription();
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
        TableColumnModel columnModel = projectTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(390);
        columnModel.getColumn(3).setPreferredWidth(20);
        columnModel.getColumn(4).setPreferredWidth(20);
    }
    public static Projects getInstance() {
        if (instance == null)
            instance = new Projects();
        return instance;
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
