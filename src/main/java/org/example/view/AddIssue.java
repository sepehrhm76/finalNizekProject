package org.example.view;

import org.example.Conroller.IssueController;
import org.example.Log.Logger;
import org.example.Model.Issue;
import org.example.Model.IssuePriority;
import org.example.Model.IssueType;
import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;


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
    Project project;
    AddIssueListener addIssueListener;

    public AddIssue(AddIssueListener addIssueListener, JButton addIssue, Issue issue, Project project) {
        this.issue = issue;
        this.project = project;
        this.addIssueListener = addIssueListener;

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
                    saveNewIssueData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Title, Description and Type fields must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                editeIssue();
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
        dialog.setResizable(false);
        dialog.add(saveBtn);


        saveBtn.setBorder(null);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(new Color(33, 51, 99));
        saveBtn.setOpaque(true);
        saveBtn.setBounds(350, 700, 120, 43);


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
                    descriptionArea.getText(),
                    tagField.getText(),
                    IssueType.fromString(typeComboBox.getSelectedItem().toString()),
                    IssuePriority.fromString(priorityComboBox.getSelectedItem().toString()),
                    null,
                    this.project.getId(),
                    null
                    );
            JOptionPane.showMessageDialog(dialog, "Issue added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            addIssueListener.onIssueCreatedOrEdited();
            dialog.dispose();
        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getLocalizedMessage());
            err.printStackTrace();
        }
    }
    public void editeIssue() {

    }
    private void showErrorPopup(String errorMessage) {
        JOptionPane.showMessageDialog(dialog, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

     interface AddIssueListener {
        void onIssueCreatedOrEdited();
    }
}
