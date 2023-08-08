package org.example.view;

import org.example.Model.Project;

import javax.swing.*;

public class InsideProjectButtons extends JPanel {
    Project project;
    Project_Board projectBoard;
    Project_allIssue projectAllIssue;
    Project_Report projectReport;
    JButton projectDetailsButton;

    public InsideProjectButtons(Project project) {
        this.project = project;
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(false);
        buttons();
    }

    public void buttons() {
        projectDetailsButton = new JButton("Project Details");
        projectDetailsButton.setBounds(520, 50, 150, 40);
        projectDetailsButton.addActionListener(e -> {
            removeAllPanels();
            ProjectDetailsPanel.getInstance().setUpData(this.project);
            ProjectDetailsPanel.getInstance().setVisible(true);
            add(ProjectDetailsPanel.getInstance());
        });
        add(projectDetailsButton);


        JButton boardsButton = new JButton("Boards");
        boardsButton.setBounds(690, 50, 150, 40);
        boardsButton.addActionListener(e -> {
            removeAllPanels();
            projectBoard = new Project_Board(project);
            UiFrame.getInstance().add(projectBoard);
            projectBoard.setVisible(true);
            add(projectBoard);
        });
        add(boardsButton);


        JButton allIssuesButton = new JButton("All Issues");
        allIssuesButton.setBounds(860, 50, 150, 40);
        allIssuesButton.addActionListener(e -> {
            removeAllPanels();
            projectAllIssue = new Project_allIssue(project);
            UiFrame.getInstance().add(projectAllIssue);
            projectAllIssue.setVisible(true);
            add(projectAllIssue);
        });
        add(allIssuesButton);

        JButton reportButton = new JButton("Reports");
        reportButton.setBounds(1030, 50, 150, 40);
        reportButton.addActionListener(e -> {
            removeAllPanels();
            projectReport = new Project_Report(project);
            UiFrame.getInstance().add(projectReport);
            projectReport.setVisible(true);
            add(projectReport);
        });
        add(reportButton);
    }
    public void removeAllPanels() {

        ProjectDetailsPanel.getInstance().setVisible(false);
        if (projectBoard != null) projectBoard.setVisible(false);
        if (projectReport != null) projectReport.setVisible(false);
        if (projectAllIssue != null) projectAllIssue.setVisible(false);
    }
}
