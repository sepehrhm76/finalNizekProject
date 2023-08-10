package org.example.view;

import org.example.Conroller.PermissionController;
import org.example.Conroller.UserController;
import org.example.Model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    private static MainPanel instance = null;
    Members members = Members.getInstance();
    Projects projects = Projects.getInstance();
    JButton projectsButton;
    JButton membersButton;
    JButton profile;


    private MainPanel(){
        setLayout(null);
        setVisible(false);
        setBackground(new Color(119,141,169));
        setBounds(0,0,300,1040);
        projectsBtn();
        membersButtonInMain();
        currentUserLabel();
        profileBtn();
    }

    public void removeAllPanels() {
        if (projects != null) {
            projects.setVisible(false);
       if (projects.insideProjectButtons != null) projects.insideProjectButtons.setVisible(false);
        }
        if (members != null) members.setVisible(false);
    }

    private void profileBtn() {
        profile = new JButton("Profile");
        profile.setBounds(25, 100, 250, 45);
        profile.setBorder(null);
        profile.setForeground(Color.white);
        profile.setBackground(new Color(33, 51, 99));
        profile.setOpaque(true);
        profile.setVisible(true);
        add(profile);

        profile.addActionListener(e -> {
            ProfileDialog dialog = new ProfileDialog(profile);
        });
    }

    private void projectsBtn() {
        projectsButton = new JButton("projects");
        projectsButton.setBounds(25, 170, 250, 45);
        projectsButton.setBorder(null);
        projectsButton.setForeground(Color.white);
        projectsButton.setBackground(new Color(33, 51, 99));
        projectsButton.setOpaque(true);
        projectsButton.setVisible(true);
        add(projectsButton);

        projectsButton.addActionListener(e -> {
            removeAllPanels();
            projects.setVisible(true);
        });
    }

    public void membersButtonInMain() {
        membersButton = new JButton("Members");
        membersButton.setBounds(25, 240, 250, 45);
        membersButton.setBorder(null);
        membersButton.setForeground(Color.white);
        membersButton.setBackground(new Color(33, 51, 99));
        membersButton.setOpaque(true);
        add(membersButton);
        membersButton.addActionListener(e -> {
            removeAllPanels();
            members.setVisible(true);
        });
        membersButton.setVisible(PermissionController.showMembers());
    }

    public void currentUserLabel() {
        if (UserController.getInstance().getCurrentUser() == null) {
            return;
        }
        JLabel memberNameHeadTitle = new JLabel(UserController.getInstance().getCurrentUser().getFirstName() + " " +
                UserController.getInstance().getCurrentUser().getLastName());
        memberNameHeadTitle.setBounds(25, -220, 1000, 500);
        memberNameHeadTitle.setForeground(Color.black);
        memberNameHeadTitle.setFont(new Font("Arial Rounded", Font.BOLD, 20));
        add(memberNameHeadTitle);

        JLabel memberRoleHeadTitle = new JLabel(UserController.getInstance().getCurrentUser().getRole().toString());
        memberRoleHeadTitle.setBounds(50, -200, 1000, 500);
        memberRoleHeadTitle.setForeground(Color.DARK_GRAY);
        memberRoleHeadTitle.setFont(new Font("Arial Rounded", Font.BOLD, 15));
        add(memberRoleHeadTitle);

    }

    public static MainPanel getInstance() {
        if (instance == null)
            instance = new MainPanel();
        return instance;
    }

    public static void reset() {
        instance = new MainPanel();
    }
}
