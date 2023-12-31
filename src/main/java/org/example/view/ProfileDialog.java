package org.example.view;

import org.example.Log.Logger;
        import org.example.Conroller.UserController;
        import org.example.Model.User;
        import org.example.Model.UserRole;
        import javax.swing.*;
        import java.awt.*;
        import java.util.Arrays;


public class ProfileDialog {
    private static final int MESSAGE_DURATION = 2000;
    Members members = Members.getInstance();
    UserController userController = UserController.getInstance();
    Timer messageTimer;
    JButton saveBtn;
    JButton logout;
    JDialog dialog;
    JTextField firstname;
    JTextField lastName;
    JTextField email;
    JPasswordField password;
    JPasswordField checkPass;
    JLabel errorLabel;
    User user;

    public ProfileDialog(JButton addUser) {

        this.user = UserController.getInstance().getCurrentUser();

        if (user == null) {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addUser), "Add User", true);

        } else {
            dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addUser), "Edit User", true);
        }

        dialog.setLayout(null);
        dialog.setResizable(false);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(UiFrame.getInstance()));


        // Create a button to save the popup
        saveBtn = new JButton("Save");
        saveBtn.setBounds(125, 420, 120, 43);
        saveBtn.setBorder(null);
        saveBtn.setForeground(Color.white);
        saveBtn.setBackground(new Color(33, 51, 99));
        saveBtn.setOpaque(true);
        dialog.add(saveBtn);

        logout = new JButton("Logout");
        logout.setBounds(255, 420, 120, 43);
        logout.setBorder(null);
        logout.setForeground(Color.white);
        logout.setBackground(new Color(33, 51, 99));
        logout.setOpaque(true);
        dialog.add(logout);

        saveBtn.addActionListener(e -> {
            updateUserData();
        });

        logout.addActionListener(e -> {

            UserController.getInstance().logout();
            UiFrame.getInstance().switchToLoginPage();
            dialog.dispose();

        });
        //firstName Field
        firstname = new JTextField();
        firstname.setBackground(Color.WHITE);
        firstname.setBounds(210, 30, 240, 40);
        firstname.setBorder(null);

        //lastName field
        lastName = new JTextField();
        lastName.setBackground(Color.WHITE);
        lastName.setBounds(210, 90, 240, 40);
        lastName.setBorder(null);

        //email field
        email = new JTextField();
        email.setBackground(Color.WHITE);
        email.setBounds(210, 150, 240, 40);
        email.setBorder(null);

        //pass field
        password = new JPasswordField();
        password.setBackground(Color.WHITE);
        password.setBounds(210, 220, 240, 40);
        password.setBorder(null);

        //correction pass
        checkPass = new JPasswordField();
        checkPass.setBackground(Color.WHITE);
        checkPass.setBounds(210, 280, 240, 40);
        checkPass.setBorder(null);

        JLabel firstnameLbl = new JLabel("User First Name:");
        firstnameLbl.setForeground(Color.black);
        firstnameLbl.setBounds(30, -205, 300, 500);
        firstnameLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JLabel lastnameLbl = new JLabel("User Last Name:");
        lastnameLbl.setForeground(Color.black);
        lastnameLbl.setBounds(30, -145, 300, 500);
        lastnameLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JLabel emailLbl = new JLabel("User Email:");
        emailLbl.setForeground(Color.black);
        emailLbl.setBounds(75, -85, 300, 500);
        emailLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JLabel passLbl = new JLabel("User Password:");
        passLbl.setForeground(Color.black);
        passLbl.setBounds(30, -10, 300, 500);
        passLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        JLabel chkPassLbl = new JLabel("Re-Enter Password:");
        chkPassLbl.setForeground(Color.black);
        chkPassLbl.setBounds(30, 45, 300, 500);
        chkPassLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));

        String[] options;
        if (user != null && user.getRole() == UserRole.SUPER_ADMIN) {
            options = new String[]{ UserRole.SUPER_ADMIN.toString() };
        } else {
            UserRole[] userRoles = UserRole.rolesWithoutSuperAdmin();
            options = new String[userRoles.length + 1];
            options[0] = "";
            for (int i = 0; i < userRoles.length; i++) {
                options[i + 1] = userRoles[i].toString();
            }
        }

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        errorLabel.setBounds(250, 195, 400, 20);
        errorLabel.setFont(new Font("Arial Rounded", Font.BOLD, 15));


        messageTimer = new Timer(MESSAGE_DURATION, e -> errorLabel.setText(""));
        messageTimer.setRepeats(false);


        email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String emailText = email.getText();
                if (!userController.isValidEmail(emailText)) {
                    errorLabel.setText("Invalid email format!");
                    messageTimer.start();
                }
            }
        });


        showUserFields();

        dialog.add(firstname);
        dialog.add(lastName);
        dialog.add(email);
        dialog.add(password);
        dialog.add(checkPass);
        dialog.add(firstnameLbl);
        dialog.add(lastnameLbl);
        dialog.add(emailLbl);
        dialog.add(passLbl);
        dialog.add(chkPassLbl);
        dialog.add(saveBtn);
        dialog.add(errorLabel);
        dialog.setVisible(true);
    }

    public void showUserFields() {
        if (user != null) {
            firstname.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            email.setText(user.getEmail());
            password.setText(user.getPassword());
            checkPass.setText(user.getPassword());
        }
    }

    public void updateUserData() {
        try {
            validateForm();

            userController.updateUser(
                    user.getId(),
                    firstname.getText(),
                    lastName.getText(),
                    email.getText().toLowerCase(),
                    new String(password.getPassword()),
                    user.getRole()
            );
            JOptionPane.showMessageDialog(dialog, "User edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);


            members.userTable.setVisible(false);
            members.userTable.setVisible(true);

            dialog.dispose();

        } catch (Exception err) {
            showErrorPopup(err.getMessage());
            Logger.getInstance().logError("Error: " + err.getMessage());
        }
    }

    public void validateForm() {
        char[] password1 = password.getPassword();
        char[] password2 = checkPass.getPassword();
        String pass1ToString = new String(password1);
        if (!userController.isValidPassword(pass1ToString)) {
            throw new IllegalArgumentException("* The password must contain at least one uppercase letter.\n" +
                    "         * The password must contain at least one lowercase letter.\n" +
                    "         * The password must contain at least one digit.\n" +
                    "         * The password must be at least 8 characters long");

        }
        if (!Arrays.equals(password1, password2)) throw new IllegalArgumentException("Passwords do not match.");
        if (firstname.getText().length() == 0) throw new IllegalArgumentException("Please enter first name.");
        if (lastName.getText().length() == 0) throw new IllegalArgumentException("Please enter last name.");
        if (!userController.isValidEmail(email.getText())) throw new IllegalArgumentException("Invalid email format.");
    }

    private void showErrorPopup(String errorMessage) {
        JOptionPane.showMessageDialog(dialog, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
