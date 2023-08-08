package org.example.view;

import org.example.Conroller.IssueController;
import org.example.Log.Logger;
import org.example.Model.Issue;
import org.example.Model.IssuePriority;
import org.example.Model.IssueType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddIssue {
    Issue issue;
    JDialog dialog;
    JButton saveBtn;
    JTextField titleField;
    JTextArea descriptionArea;
    JTextField tagField;
    JComboBox<IssueType> typeComboBox;
    JComboBox<IssuePriority> priorityComboBox;
    IssueController issueController = new IssueController();

    public AddIssue(JButton addIssue, Issue issue) {
        this.issue = issue;

        if (issue == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addIssue), "Add Issue", true);
        } else {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addIssue), "Edit Issue", true);
        }

        initFields();
        setupUI();
        showAddIssueDialog();
    }

    public void showAddIssueDialog() {
        dialog.setLayout(null);
        dialog.setResizable(false);
        dialog.setSize(800, 800);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(UiFrame.getInstance()));
        dialog.setVisible(true);
    }

    private void initFields() {
        titleField = new JTextField();

        descriptionArea = new JTextArea();

        tagField = new JTextField();

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem(null);
        for (IssueType type : IssueType.values()) {
            typeComboBox.addItem(type);
        }

        priorityComboBox = new JComboBox<>();
        priorityComboBox.addItem(null);
        for (IssuePriority priority : IssuePriority.values()) {
            priorityComboBox.addItem(priority);
        }


        saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            if (issue == null) {
                if (isValidInput()) {
                } else {
                    JOptionPane.showMessageDialog(dialog, "Title, Description and Type fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean isValidInput() {
        return !titleField.getText().isEmpty() && !descriptionArea.getText().isEmpty() &&
                typeComboBox.getSelectedItem() != null;
    }

    private void setupUI() {
        dialog.add(createLabel("Title:", 90, 10, 100, 25));
        dialog.add(titleField);
        titleField.setBounds(200, 10, 500, 25);

        dialog.add(createLabel("Description:", 90, 50, 100, 25));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        dialog.add(descriptionScrollPane);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionScrollPane.setBounds(200, 50, 500, 200);

        dialog.add(createLabel("Tag:", 90, 300, 100, 25));
        dialog.add(tagField);
        tagField.setBounds(140, 300, 560, 25);

        dialog.add(createLabel("Type:", 90, 350, 100, 25));
        dialog.add(typeComboBox);
        typeComboBox.setBounds(140, 350, 200, 25);

        dialog.add(createLabel("Priority:", 430, 350, 100, 25));
        dialog.add(priorityComboBox);
        priorityComboBox.setBounds(500, 350, 200, 25);

        dialog.add(createLabel("Assign to user:", 90, 500, 100, 25));

        dialog.add(saveBtn);
        saveBtn.setBounds(350, 700, 100, 25);

        dialog.setResizable(false);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        return label;
    }
    public void saveNewIssueData() {
        try {

            issueController.addIssue(
                    titleField.getText(),
                    descriptionArea.getText()
            );
            JOptionPane.showMessageDialog(dialog, "Project added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            projects.projectTable.setVisible(false);
            projects.projectTable.setVisible(true);

            dialog.dispose();

        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getMessage());
        }
    }
}
