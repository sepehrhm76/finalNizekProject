package org.example.view;

import org.example.Conroller.Project_UserController;
import org.example.Conroller.UserController;
import org.example.Model.Project;
import org.example.Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProjectDetailsPanel extends JPanel {
    private static ProjectDetailsPanel instance = null;
    private Project project;
    private Project_UserController projectUserController = Project_UserController.getInstance();
    UserController userController = new UserController();
    private JList<User> userList;
    private DefaultListModel<User> listModel;
    private JButton addButton;
    JLabel titleLabel;
    private ProjectDetailsPanel() {
        setLayout(null);
        setBounds(300,0,1140,1040);
        setVisible(false);
        titleLabel = new JLabel();
        add(titleLabel);
        listModel();
        addUserToProjectBtn();
    }

    public void setUpData(Project project) {
        this.project = project;
        titleLabel.setText(this.project.getName() + " Project");
        titleLabel.setFont(new Font("Arial Rounded", Font.BOLD, 30));
        titleLabel.setBounds(450,60,500,100);

        updateList();
    }

    public void listModel() {
        listModel = new DefaultListModel<>();
        userList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(350, 200, 400, 400);
        add(scrollPane);
    }

    public void addUserToProjectBtn() {
        addButton = new JButton("Add Users to Project");
        addButton.setBounds(450, 650, 200, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> selectedUsers = userList.getSelectedValuesList();

                if (selectedUsers.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No users selected.");
                } else {
                    for (User user : selectedUsers) {
                        projectUserController.addUserToProject(user, project);
                    }
                    JOptionPane.showMessageDialog(null, "Users added to project successfully.");
                    updateList(); // Refresh the user list after adding users
                }
            }
        });
        add(addButton);
    }
    private void updateList() {
        listModel.clear();
        List<User> allUsers = userController.getAllUser();
        List<User> usersInProject = projectUserController.getUsersByProject(project);

        for (User user : allUsers) {
            if (!usersInProject.contains(user)) {
                listModel.addElement(user);
            }
        }
    }
    public static ProjectDetailsPanel getInstance() {
        if (instance == null)
            instance = new ProjectDetailsPanel();
        return instance;
    }
}
