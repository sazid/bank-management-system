package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.components.StyledLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUI extends JFrame implements ActionListener {

    private JPanel panel;
    private StyledLabel introLabel, usernameLabel, passwordLabel;
    private StyledButton loginButton;
    private JTextField usernameTf;
    private JPasswordField passwordTf;

    public LoginUI() {
        super("Bank Management System | Login");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        initUI();
        bind();
    }

    private void initUI() {
        panel = new JPanel();
        panel.setLayout(null);

        introLabel = new StyledLabel("Bank Management System");
        introLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
        introLabel.setBounds(250, 40, 400, 50);

        usernameLabel = new StyledLabel("Username: ");
        usernameLabel.setBounds(230, 180, 200, 30);

        usernameTf = new JTextField();
        usernameTf.setBounds(300, 180, 200, 30);

        passwordLabel = new StyledLabel("Password: ");
        passwordLabel.setBounds(230, 220, 200, 30);

        passwordTf = new JPasswordField();
        passwordTf.setBounds(300, 220, 200, 30);

        loginButton = new StyledButton("Login");
        loginButton.setBounds(300, 260, 200, 35);

        panel.add(introLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(usernameTf);
        panel.add(passwordTf);
        panel.add(loginButton);
        add(panel);
    }

    private void bind() {
        loginButton.addActionListener(this);
        passwordTf.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == loginButton) {
            verifyLogin();
        } else if (src == passwordTf) {
            verifyLogin();
        }
    }

    private void verifyLogin() {
        String username = usernameTf.getText();
        String password = new String(passwordTf.getPassword());

        Connection connection = ConnectionManager.getInstance()
                .getConnection();
        try {
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM login WHERE username=? AND password=?");
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                UserLoginInfo info = new UserLoginInfo();
                info.name = rs.getString("name");
                info.username = rs.getString("username");
                info.status = status;

                if (status.equals("employee")) {
                    new EmployeeUI(info).setVisible(true);
                } else if (status.equals("customer")) {
                    new CustomerUI(info).setVisible(true);
                }
                setVisible(false);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password. Please try again.");

                // clear the text fields
                usernameTf.setText("");
                passwordTf.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(this,
                    "Error! Failed to query database.");
        }
    }

}
