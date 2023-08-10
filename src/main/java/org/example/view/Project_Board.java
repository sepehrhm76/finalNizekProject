package org.example.view;

import org.example.Conroller.BoardController;
import org.example.Model.Board;
import org.example.Model.Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Project_Board extends JPanel implements TableModel, AddBoard.AddBoardListener {
    Project project;
    JTable boardTable = new JTable();
    JButton addBoard;
    BoardController boardController = new BoardController();
    private JTextField searchField;
    JLabel searchLabel;


    public Project_Board(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(true);
        setBackground(Color.WHITE);
        addBoardButton();
        createTable();
        headTitle();

    }

    public void headTitle() {
        JLabel boardHeadTitle = new JLabel("Boards");
        boardHeadTitle.setBounds(350, -100, 1000, 500);
        boardHeadTitle.setForeground(new Color(33, 51, 99));
        boardHeadTitle.setFont(new Font("Arial Rounded", Font.BOLD, 100));
        add(boardHeadTitle);
    }

    public void addBoardButton() {
        addBoard = new JButton("Add Board");
        addBoard.setBounds(1020, 200, 120, 40);
        addBoard.setBorder(null);
        addBoard.setForeground(Color.white);
        addBoard.setBackground(new Color(33, 51, 99));
        addBoard.setOpaque(true);
        add(addBoard);
        addBoard.addActionListener(e -> {
            AddBoard addBoard1 = new AddBoard(this,addBoard, null,this.project);
        });
    }

    public void createTable() {
        boardTable.setModel(this);
        JScrollPane scrollPane = new JScrollPane(boardTable);
        scrollPane.setBounds(100, 300, 940, 450);
        add(scrollPane, BorderLayout.CENTER);

        JTableHeader tableHeader = boardTable.getTableHeader();
        tableHeader.setFont(new Font("Arial Rounded", Font.BOLD, 12));
        tableHeader.setBackground(new Color(33, 51, 99));
        tableHeader.setForeground(Color.white);

        setColumnWidths();
        boardTable.setRowSelectionAllowed(false);
        for (int i = 0; i <= 3; i++) {
            boardTable.getColumnModel().getColumn(i).setCellRenderer(new NonSelectableCellRenderer());
        }
        boardTable.getColumn("Edit").setCellRenderer(new Project_Board.ButtonRenderer("Edit"));
        boardTable.getColumn("Edit").setCellEditor(new Project_Board.ButtonEditor("Edit", new JCheckBox(), rowIndex -> {

            AddBoard editeBoardData = new AddBoard(this,addBoard, boardController.getBoardsByProjectId(this.project.getId()).get(rowIndex),this.project);
        }));

        boardTable.getColumn("Delete").setCellRenderer(new Project_Board.ButtonRenderer("Delete"));
        boardTable.getColumn("Delete").setCellEditor(new Project_Board.ButtonEditor("Delete", new JCheckBox(), rowIndex -> {
            int option = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this board?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                Board boardToDelete = boardController.getBoardsByProjectId(this.project.getId()).get(rowIndex);
                boardController.removeBoard(boardToDelete);
                boardTable.setVisible(false);
                boardTable.setVisible(true);
            }
        }));

        boardTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int row = boardTable.rowAtPoint(e.getPoint());
                    int column = boardTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 0) {
                        String columnTitle = boardTable.getColumnName(column);
                        Object cellValue = boardTable.getValueAt(row, column);
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
        return boardController.getBoardsByProjectId(this.project.getId()).size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "ID";
            case 1: return "Name";
            case 2: return "Edit";
            case 3: return "Delete";
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return boardController.getBoardsByProjectId(this.project.getId()).get(rowIndex).getId();
            case 1: return boardController.getBoardsByProjectId(this.project.getId()).get(rowIndex).getName();
            default: return null;
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
        TableColumnModel columnModel = boardTable.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(20);
        columnModel.getColumn(3).setPreferredWidth(20);
    }

    @Override
    public void onBoardCreatedOrEdited() {
        boardTable.setVisible(false);
        boardTable.setVisible(true);
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