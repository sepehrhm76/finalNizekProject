package org.example.view;

import org.example.Manager.UserManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private static LoginPanel instance = null;
    private static final int MESSAGE_DURATION = 2000;
    UserManager userManager = UserManager.getInstance();
    MainPanel mainPanel = MainPanel.getInstance();
    private JLabel errorLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private Timer messageTimer;


    JPanel middlePanel = new JPanel() {
        //For rounded corners
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.WHITE);
            g.fillRoundRect(0, 0, 500, 560, 30, 30);
            g.setColor(new Color(219, 234, 255));
            g.fillRoundRect(125, 255, 245, 50, 30, 30);
            g.fillRoundRect(125, 355, 245, 50, 30, 30);
            g.setColor(new Color(33, 51, 99));
            g.fillRoundRect(185, 450, 130, 50, 30, 30);
        }
    };



    private LoginPanel() {

        setLayout(null);
        setBackground(new Color(33, 51, 99));
        setSize(1440,1040);
        headerLabel();
        middlePanel();
        emailLabel();
        passwordLabel();
        emailFieldOption();
        passwordFieldOption();
        middlePanelHeader1();
        middlePanelHeader2();
        errorLabel();
        loginButtonAndListener();
    }
    public void headerLabel() {
        JLabel headerLabel = new JLabel("Sepiaj PMT");
        headerLabel.setForeground(Color.white);
        headerLabel.setBounds(440, -70, 1000, 500);
        headerLabel.setFont(new Font("Arial Rounded", Font.BOLD, 100));
        add(headerLabel);
    }
    public void middlePanel() {
        //Middle panel setUp
        mainPanel.setVisible(false);
        middlePanel.setBounds(460, 340, 500, 560);
        middlePanel.setVisible(true);
        middlePanel.setBackground(new Color(33, 51, 99));
        middlePanel.setLayout(null);
        add(middlePanel);
    }
    public void emailLabel() {
        JLabel emailLabel = new JLabel("Enter your Email Address:");
        emailLabel.setForeground(Color.black);
        emailLabel.setBounds(135, -20, 300, 500);
        emailLabel.setFont(new Font("Arial Rounded", Font.BOLD, 17));
        middlePanel.add(emailLabel);
    }
    public void passwordLabel() {
        JLabel passwordLabel = new JLabel("Enter your Password:");
        passwordLabel.setForeground(Color.black);
        passwordLabel.setBounds(157, 83, 300, 500);
        passwordLabel.setFont(new Font("Arial Rounded", Font.BOLD, 16));
        middlePanel.add(passwordLabel);
    }
    public void emailFieldOption() {
        emailField = new JTextField();
        emailField.setBackground(new Color(219, 234, 255));
        emailField.setBounds(130, 270, 240, 20);
        emailField.setBorder(null);
        middlePanel.add(emailField);
    }
    public void passwordFieldOption() {
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(219, 234, 255));
        passwordField.setBounds(130, 370, 240, 20);
        passwordField.setBorder(null);
        middlePanel.add(passwordField);
    }
    public void middlePanelHeader1() {
        JLabel secondPanelLabel = new JLabel("Log In To Manage your Projects");
        secondPanelLabel.setForeground(Color.black);
        secondPanelLabel.setBounds(45, -210, 1000, 500);
        secondPanelLabel.setFont(new Font("Arial Rounded", Font.BOLD, 25));
        middlePanel.add(secondPanelLabel);
    }
    public void middlePanelHeader2() {
        JLabel secondPanelLabelB = new JLabel("EASILY");
        secondPanelLabelB.setForeground(new Color(33, 51, 99));
        secondPanelLabelB.setBounds(120, -130, 1000, 500);
        secondPanelLabelB.setFont(new Font("Arial Rounded", Font.BOLD, 70));
        middlePanel.add(secondPanelLabelB);
    }
    public void errorLabel() {
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        errorLabel.setBounds(150, 415, 400, 20);
        errorLabel.setFont(new Font("Arial Rounded", Font.BOLD, 15));
        middlePanel.add(errorLabel);
    }
    public void loginButtonAndListener() {
        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(190, 454, 120, 43);
        loginButton.setBorder(null);
        loginButton.setForeground(Color.white);
        loginButton.setBackground(new Color(33, 51, 99));
        loginButton.setOpaque(true);
        loginButton.setVisible(true);
        middlePanel.add(loginButton);

        messageTimer = new Timer(MESSAGE_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorLabel.setText("");
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (userManager.validateUserLogin(email, password)) {
                    UiFrame.getInstance().switchToMainPanel();


                } else {
                    errorLabel.setText("Invalid email or password!");
                    messageTimer.start();
                }
            }
        });

        //handle to press Login button with Enter
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });

        emailField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (!userManager.isValidEmail(emailField.getText())) {
                    errorLabel.setText("Invalid email format!");
                    messageTimer.start();
                }
            }
        });


        messageTimer.setRepeats(false);
    }
    public static LoginPanel getInstance() {
        if (instance == null)
            instance = new LoginPanel();
        return instance;
    }
}
