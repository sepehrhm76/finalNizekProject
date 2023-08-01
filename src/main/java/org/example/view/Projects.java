package org.example.view;

import org.example.Conroller.ProjectController;
import org.example.Log.Logger;
import org.example.Model.Project;
import org.example.Model.User;
import org.example.Model.UserRole;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Projects extends JPanel implements TableModel {
    private static Projects instance = null;
    public JTable projectTable = new JTable();
    private JButton addProject;
    ProjectController projectController = new ProjectController();
    
    public Projects(){
        setLayout(null);
        setVisible(false);
        setBounds(300,0,1140,1040);
        setBackground(Color.cyan);
        createTable();
    }

    public void createTable() {
        projectTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setBounds(100, 95, 940, 895);
        add(scrollPane, BorderLayout.CENTER);
        setColumnWidths();
        projectTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 4; i++) {
            projectTable.getColumnModel().getColumn(i).setCellRenderer(new Projects.NonSelectableCellRenderer());
        }
        projectTable.getColumn("Edit").setCellRenderer(new Projects.ButtonRenderer("Edit"));
        projectTable.getColumn("Edit").setCellEditor(new Projects.ButtonEditor("Edit", new JCheckBox(), new Members.ButtonCallback() {
            @Override
            public void onClick(int rowIndex) {

//                AddUser addUserObject = new AddUser(addUser, userController.getAllUser().get(rowIndex));
            }
        }));

            projectTable.getColumn("Delete").setCellRenderer(new Projects.ButtonRenderer("Delete"));
            projectTable.getColumn("Delete").setCellEditor(new Projects.ButtonEditor("Delete", new JCheckBox(), new Members.ButtonCallback() {
                @Override
                public void onClick(int rowIndex) {
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
                }
            }));

            projectTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Check for double-click (left mouse button)
                    if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                        int row = projectTable.rowAtPoint(e.getPoint());
                        int column = projectTable.columnAtPoint(e.getPoint());
                        // Only execute if the double click happened on a valid row and column
                        if (row >= 0 && column >= 0) {
                            // Get the value of the cell and print it
                            Object cellValue = projectTable.getValueAt(row, column);
                            System.out.println("Double-clicked: " + cellValue);
                            // Add your desired functionality here when a row is double-clicked
                        }
                    }
                }
            });
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
        if (columnIndex == 3 && columnIndex == 4) return JButton.class;
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
            case 3 -> null;
            case 4 -> null;
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
        columnModel.getColumn(2).setPreferredWidth(350);
        columnModel.getColumn(3).setPreferredWidth(40);
        columnModel.getColumn(3).setPreferredWidth(40);
    }
    public static Projects getInstance() {
        if (instance == null)
            instance = new Projects();
        return instance;
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
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
        private final Members.ButtonCallback callback;
        private boolean isPushed;
        private int currentRow;
        private String title;
        public ButtonEditor(String title, JCheckBox checkBox, Members.ButtonCallback callback) {
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
    interface ButtonCallback {
        void onClick(int rowIndex);
    }
    private class NonSelectableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
            return component;
        }
    }
}
