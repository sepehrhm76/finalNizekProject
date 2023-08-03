package org.example.view;

import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectDetailsPanel extends JPanel {
    private final Project project;
    public ProjectDetailsPanel(Project project) {
        this.project = project;
        initUI();
    }
    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Project Title:");
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 16));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial Rounded", Font.BOLD, 16));

        JLabel titleLabelValue = new JLabel(project.getName());
        titleLabelValue.setFont(new Font("Arial Rounded", Font.PLAIN, 16));

        JTextArea descriptionTextArea = new JTextArea(project.getDescription());
        descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(titleLabel);
        titlePanel.add(titleLabelValue);

        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(new JScrollPane(descriptionTextArea), BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(descriptionPanel, BorderLayout.CENTER);
    }

}
