package org.example.view;

import org.example.Conroller.Project_UserController;
import org.example.Conroller.UserController;
import org.example.Model.Project;
import org.example.Model.User;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectDetailsPanel extends JPanel {
    private static ProjectDetailsPanel instance = null;
    private Project project;
    private Project_UserController projectUserController = Project_UserController.getInstance();
    UserController userController = new UserController();
    JLabel titleLabel;
    private JButton manageMembersButton;
    JDialog manageMembersDialog;

    private ProjectDetailsPanel() {
        setLayout(null);
        setBounds(300, 0, 1140, 1040);
        setVisible(false);
        titleLabel = new JLabel();
        add(titleLabel);
        manageMemberBtn();
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

            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    User selectedUser = userList.get(i);
                    projectUserController.removeUserFromProject(selectedUser, this.project);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(deleteMemberBtn);
        buttonPanel.add(addMemberBtn);
        manageMembersDialog.add(buttonPanel, BorderLayout.PAGE_START);
        manageMembersDialog.setVisible(true);
    }

    public void openPageToShowAllToAddProjectMember() {
        JDialog addMember = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Members To This Project", true);
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
        JButton closeButton = new JButton("Add");
        closeButton.addActionListener(e -> {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    User selectedUser = userList.get(i);
                    projectUserController.addUserToProject(selectedUser, this.project);
                }
            }
            addMember.dispose();

        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        addMember.add(buttonPanel, BorderLayout.SOUTH);
        addMember.setVisible(true);
    }

    public void manageMemberBtn() {
        manageMembersButton = new JButton("Manage Project Members");
        manageMembersButton.setBounds(880, 100, 240, 40);
        manageMembersButton.addActionListener(e -> openManageMembersPopup());
        add(manageMembersButton);
    }


    public void setUpData(Project project) {
        this.project = project;
        titleLabel.setText(this.project.getName() + " Project");
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 30));
        titleLabel.setBounds(450,60,500,100);
    }

    public static ProjectDetailsPanel getInstance() {
        if (instance == null)
            instance = new ProjectDetailsPanel();
        return instance;
    }
}