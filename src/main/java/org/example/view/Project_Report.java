package org.example.view;

import org.example.Model.Project;

import javax.swing.*;

public class Project_Report extends JPanel {
    Project project;

    public Project_Report(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(false);
        buttons();
        JButton jButton = new JButton("report");
        jButton.setBounds(500,500,100,100);
        add(jButton);
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
        allIssuesButton.addActionListener(e -> {
            setVisible(false);
            Project_allIssue projectAllIssue = new Project_allIssue(this.project);
            UiFrame.getInstance().add(projectAllIssue);
            projectAllIssue.setVisible(true);
        });
        add(allIssuesButton);

        JButton reportButton = new JButton("Reports");
        reportButton.setBounds(830, 50, 150, 40);
        allIssuesButton.addActionListener(e -> {

        });
        add(reportButton);
    }
}
