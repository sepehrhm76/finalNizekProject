package org.example.view;

import org.example.Conroller.BoardController;
import org.example.Log.Logger;
import org.example.Model.Board;
import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;

public class AddBoard {
    BoardController boardController = new BoardController();
    Board board;
    JDialog dialog;
    JTextField nameFiled;
    JButton saveBtn;
    AddBoardListener addBoardListener;
    Project project;

    public AddBoard(AddBoardListener addBoardListener,JButton addBoard, Board board, Project project) {
        this.addBoardListener = addBoardListener;
        this.project = project;
        this.board = board;
        if (board == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addBoard), "Add Board", true);

        } else {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addBoard), "Edit Board", true);
        }
        addBoardDialog();
    }

    public void showBoardFields() {
        if (board != null) {
            nameFiled.setText(board.getName());
        }
    }

    public void addBoardDialog() {
        dialog.setLayout(null);
        dialog.setResizable(false);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(null);


        nameFiled = new JTextField();
        nameFiled.setBackground(Color.WHITE);
        nameFiled.setBounds(210, 30, 240, 40);
        nameFiled.setBorder(null);

        JLabel nameLabel = new JLabel("Board Name:");
        nameLabel.setForeground(Color.black);
        nameLabel.setBounds(50, -200, 300, 500);
        nameLabel.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        saveBtn = new JButton("Save");
        saveBtn.setBounds(190, 420, 120, 43);
        saveBtn.setBorder(null);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(new Color(33, 51, 99));
        saveBtn.setOpaque(true);
        dialog.add(saveBtn);
        saveBtn.addActionListener(e -> {
            if (board != null) {
                updateBoardData();
            } else {
                saveNewBoardData();
            }
        });
        showBoardFields();
        dialog.add(nameFiled);
        dialog.add(nameLabel);
        dialog.add(saveBtn);
        dialog.setVisible(true);

    }

    public void saveNewBoardData() {
        try {

            boardController.addBoard(
                    nameFiled.getText(),
                    this.project.getId()
            );
            JOptionPane.showMessageDialog(dialog, "Board added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            addBoardListener.onBoardCreatedOrEdited();
            dialog.dispose();

        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getMessage());
        }
    }

    private void showErrorPopup(String errorMessage) {
        JOptionPane.showMessageDialog(dialog, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateBoardData() {
        try {
            boardController.updateBoard(
                    board.getId(),
                    nameFiled.getText(),
                    this.project.getId()
            );
            JOptionPane.showMessageDialog(dialog, "Board edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            addBoardListener.onBoardCreatedOrEdited();
            dialog.dispose();

        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getMessage());
        }
    }

    interface AddBoardListener {
        void onBoardCreatedOrEdited();
    }

}
