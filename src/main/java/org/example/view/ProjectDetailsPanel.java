package org.example.view;

import org.example.Conroller.Project_UserController;
import org.example.Conroller.UserController;
import org.example.Model.Project;
import org.example.Model.User;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class ProjectDetailsPanel extends JPanel {
    private static ProjectDetailsPanel instance = null;
    private Project project;
    private final Project_UserController projectUserController = Project_UserController.getInstance();
    UserController userController = new UserController();
    JLabel titleLabel;
    JDialog manageMembersDialog;
    JDialog addMember;
    private JTextArea descriptionTextArea;

    private ProjectDetailsPanel() {
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(false);
        titleLabel = new JLabel();
        descriptionTextArea = new JTextArea();
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setBounds(200, 300, 650, 500);
        add(descriptionScrollPane);
        add(titleLabel);
        manageMemberBtn();
        buttons();

    }

    public void openManageMembersPopup() {
        manageMembersDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Manage Members", true);
        manageMembersDialog.setLayout(new BorderLayout());
        manageMembersDialog.setSize(700, 700);
        manageMembersDialog.setLocationRelativeTo(null);
        manageMembersDialog.setResizable(false);
        List<User> userList = projectUserController.getUsersByProject(this.project);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        List<JCheckBox> checkBoxes = new ArrayList<>();

        for (User user: userList) {
            String userData = user.getFirstName() + "  -  " +
                    user.getLastName() + "  -  " +
                    user.getEmail() + "  -  " +
                    user.getRole();
            JCheckBox checkBox = new JCheckBox(userData);
            checkBoxes.add(checkBox);
            contentPanel.add(checkBox);

        }
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        manageMembersDialog.add(scrollPane, BorderLayout.CENTER);
        JButton addMemberBtn = new JButton("Add Member");
        addMemberBtn.addActionListener(e -> openPageToShowAllToAddProjectMember());
        JButton deleteMemberBtn = new JButton("Delete Member");
        deleteMemberBtn.addActionListener(e -> {
            int selectedCount = 0;
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selectedCount++;
                }
            }

            if (selectedCount == 0) {
                JOptionPane.showMessageDialog(this, "No members selected for deletion.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int confirmationResult = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the selected member(s)?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (confirmationResult == JOptionPane.YES_OPTION) {
                for (int i = checkBoxes.size() - 1; i >= 0; i--) {
                    if (checkBoxes.get(i).isSelected()) {
                        User selectedUser = userList.get(i);
                        projectUserController.removeUserFromProject(selectedUser, this.project);
                    }
                }
            }
            manageMembersDialog.setVisible(false);
            openManageMembersPopup();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteMemberBtn);
        buttonPanel.add(addMemberBtn);
        manageMembersDialog.add(buttonPanel, BorderLayout.PAGE_START);
        manageMembersDialog.setVisible(true);
    }

    public void openPageToShowAllToAddProjectMember() {
        addMember = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Members To This Project", true);
        addMember.setSize(500, 500);
        addMember.setLocationRelativeTo(null);
        addMember.setResizable(false);
        List<User> userList = userController.getAllUsersNotInProject(this.project.getId());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        List<JCheckBox> checkBoxes = new ArrayList<>();

        for (User user : userList) {
            String userData = user.getFirstName() + "  -  " +
                    user.getLastName() + "  -  " +
                    user.getEmail() + "  -  " +
                    user.getRole();
            JCheckBox checkBox = new JCheckBox(userData);
            checkBoxes.add(checkBox);
            contentPanel.add(checkBox);
        }


        JScrollPane scrollPane = new JScrollPane(contentPanel);
        addMember.add(scrollPane, BorderLayout.CENTER);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            int addedMembersCount = 0;
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    User selectedUser = userList.get(i);
                    projectUserController.addUserToProject(selectedUser, this.project);
                    addedMembersCount++;
                }
            }
            if (addedMembersCount > 0) {
                JOptionPane.showMessageDialog(null, addedMembersCount + " member(s) added.");
                addMember.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "No members selected.");
                addMember.dispose();
            }
            manageMembersDialog.dispose();
            openManageMembersPopup();
            addMember.dispose();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        addMember.add(buttonPanel, BorderLayout.SOUTH);
        addMember.setVisible(true);
    }

    public void manageMemberBtn() {
        JButton manageMembersButton = new JButton("Manage Project Members");
        manageMembersButton.setBounds(880, 100, 240, 40);
        manageMembersButton.addActionListener(e -> openManageMembersPopup());
        add(manageMembersButton);
    }


    public void setUpData(Project project) {
        this.project = project;
        titleLabel.setText(this.project.getName() + " Project");
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 30));
        titleLabel.setBounds(400,100,500,100);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionTextArea.setText(this.project.getDescription());
    }

    public void buttons() {
        JButton projectDetailsButton = new JButton("Project Details");
        projectDetailsButton.setBounds(20, 50, 150, 40);
        add(projectDetailsButton,0);

        JButton boardsButton = new JButton("Boards");
        boardsButton.setBounds(190, 50, 150, 40);
        boardsButton.addActionListener(e -> {
           Project_Board projectBoard = new Project_Board(this.project);
           setVisible(false);
           UiFrame.getInstance().add(projectBoard);
           projectBoard.setVisible(true);
        });
        add(boardsButton,0);

        JButton allIssuesButton = new JButton("All Issues");
        allIssuesButton.setBounds(360, 50, 150, 40);
        allIssuesButton.addActionListener(e -> {
           Project_allIssue projectAllIssue = new Project_allIssue(this.project);
            setVisible(false);
            UiFrame.getInstance().add(projectAllIssue);
            projectAllIssue.setVisible(true);
        });
        add(allIssuesButton,0);

        JButton reportButton = new JButton("Reports");
        reportButton.setBounds(530, 50, 150, 40);
        reportButton.addActionListener(e -> {
            Project_Report projectReport = new Project_Report(this.project);
            setVisible(false);
            UiFrame.getInstance().add(projectReport);
            projectReport.setVisible(true);
        });
        add(reportButton,0);
    }

    public static ProjectDetailsPanel getInstance() {
        if (instance == null)
            instance = new ProjectDetailsPanel();
        return instance;
    }
}