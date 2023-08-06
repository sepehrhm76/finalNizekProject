package org.example.view;

import org.example.Conroller.ProjectController;
import org.example.Log.Logger;
import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;

public class AddProject {
    Projects projects = Projects.getInstance();
    ProjectController projectController = new ProjectController();
    Project project;
    JDialog dialog;
    JTextField nameFiled;
    JTextArea descriptionField;
    JButton saveBtn;

    public AddProject(JButton addProject, Project project) {
        this.project = project;
        if (project == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addProject), "Add Project", true);

        } else {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addProject), "Edit Project", true);
        }
        addProjectDialog();
    }

    public void showProjectFields() {
        if (project != null) {
            nameFiled.setText(project.getName());
            descriptionField.setText(project.getDescription());
        }
    }

    public void addProjectDialog() {
        dialog.setLayout(null);
        dialog.setResizable(false);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(null);


        nameFiled = new JTextField();
        nameFiled.setBackground(Color.WHITE);
        nameFiled.setBounds(210, 30, 240, 40);
        nameFiled.setBorder(null);

        descriptionField = new JTextArea();
        descriptionField.setBackground(Color.WHITE);
        descriptionField.setBounds(20,150,460,250);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);

        JLabel nameLabel = new JLabel("Project Name:");
        nameLabel.setForeground(Color.black);
        nameLabel.setBounds(50, -200, 300, 500);
        nameLabel.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JLabel descriptionLabel = new JLabel("Descriptions:");
        descriptionLabel.setForeground(Color.black);
        descriptionLabel.setBounds(20, -120, 300, 500);
        descriptionLabel.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        descriptionScrollPane.setBounds(20, 150, 460, 250);
        dialog.add(descriptionScrollPane);

        saveBtn = new JButton("Save");
        saveBtn.setBounds(190, 420, 120, 43);
        saveBtn.setBorder(null);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(new Color(33, 51, 99));
        saveBtn.setOpaque(true);
        dialog.add(saveBtn);
        saveBtn.addActionListener(e -> {
            if (project != null) {
                updateProjectData();
            } else {
                saveNewProjectData();
            }
        });
        showProjectFields();
        dialog.add(nameFiled);
        dialog.add(descriptionScrollPane);
        dialog.add(nameLabel);
        dialog.add(descriptionLabel);
        dialog.add(saveBtn);
        dialog.setVisible(true);

    }

    public void saveNewProjectData() {
        try {

            projectController.addProject(
                    nameFiled.getText(),
                    descriptionField.getText()
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

    private void showErrorPopup(String errorMessage) {
        JOptionPane.showMessageDialog(dialog, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void updateProjectData() {
        try {
            projectController.updateProject(
                    project.getId(),
                    nameFiled.getText(),
                    descriptionField.getText()
            );
            JOptionPane.showMessageDialog(dialog, "Project edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);


            projects.projectTable.setVisible(false);
            projects.projectTable.setVisible(true);

            dialog.dispose();

        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getMessage());
        }
    }

}
