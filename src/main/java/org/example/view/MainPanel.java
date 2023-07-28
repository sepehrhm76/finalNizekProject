package org.example.view;

import org.example.Manager.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class MainPanel extends JPanel {
    UserManager userManager = UserManager.getInstance();
    private  Timer messageTimer;
    private static final int MESSAGE_DURATION = 2000;

    public MainPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        setVisible(true);

        JButton addUser = new JButton("Add User");
        addUser.setBounds(100, 100, 120, 43);
        addUser.setBorder(null);
        addUser.setForeground(Color.white);
        addUser.setBackground(new Color(33, 51, 99));
        addUser.setOpaque(true);
        add(addUser);



        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(addUser), "Add User", true);
                dialog.setLayout(null);
                dialog.setResizable(false);
                dialog.setSize(500, 500);
                dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(UiFrame.getInstance()));


                // Create a button to save the popup
                JButton saveBtn = new JButton("Save");
                saveBtn.setBounds(190, 420, 120, 43);
                saveBtn.setBorder(null);
                saveBtn.setForeground(Color.white);
                saveBtn.setBackground(new Color(33, 51, 99));
                saveBtn.setOpaque(true);
                dialog.add(saveBtn);
                saveBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose(); // Close the popup when the button is clicked
                    }
                });
                //firstName Field
                JTextField firstname = new JTextField();
                firstname.setBackground(new Color(219, 234, 255));
                firstname.setBounds(210, 30, 240, 40);
                firstname.setBorder(null);

                //lastName field
                JTextField lastName = new JTextField();
                lastName.setBackground(new Color(219, 234, 255));
                lastName.setBounds(210, 90, 240, 40);
                lastName.setBorder(null);

                //email field
                JTextField email = new JTextField();
                email.setBackground(new Color(219, 234, 255));
                email.setBounds(210, 150, 240, 40);
                email.setBorder(null);

                //pass field
                JPasswordField password = new JPasswordField();
                password.setBackground(new Color(219, 234, 255));
                password.setBounds(210, 220, 240, 40);
                password.setBorder(null);

                //correction pass
                JPasswordField checkPass = new JPasswordField();
                checkPass.setBackground(new Color(219, 234, 255));
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

                JLabel roleLbl = new JLabel("User Role:");
                roleLbl.setForeground(Color.black);
                roleLbl.setBounds(30, 125, 300, 500);
                roleLbl.setFont(new Font("Arial Rounded", Font.BOLD, 17));


                String[] options = {"PO", "QA", "DEV"};
                JComboBox<String> dropdown = new JComboBox<>(options);
                dropdown.setBounds(210,360,240,40);

                JLabel errorLabel = new JLabel("");
                errorLabel.setForeground(Color.red);
                errorLabel.setBounds(250, 195, 400, 20);
                errorLabel.setFont(new Font("Arial Rounded", Font.BOLD, 15));

                messageTimer = new Timer(MESSAGE_DURATION, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        errorLabel.setText("");
                    }
                });
                messageTimer.setRepeats(false);


                email.addFocusListener(new java.awt.event.FocusAdapter() {
                    public void focusLost(java.awt.event.FocusEvent evt) {
                        String emailText = email.getText();
                        if (!userManager.isValidEmail(emailText)) {
                            errorLabel.setText("Invalid email format!");
                            messageTimer.start();
                        }
                    }
                });


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
                dialog.add(roleLbl);
                dialog.add(saveBtn);
                dialog.add(dropdown);
                dialog.add(errorLabel);
                dialog.setVisible(true);

            }

        });
    }
}
