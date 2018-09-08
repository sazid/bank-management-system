package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.components.StyledButton;
import m6.components.StyledLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is responsible for showing the top title, name and logout button in
 * all the other pages. It also contains common functions like the back button.
 *
 * So any user related UI must extend this class.
 */
public class UserBaseUI extends JFrame implements ActionListener {

    public JPanel mainPanel;
    public StyledButton backButton, logoutButton;
    private StyledLabel titleLabel, nameLabel, extraInfoLabel;
    private UserLoginInfo loginInfo;
    private JSeparator separator = new JSeparator();

    public UserBaseUI(UserLoginInfo loginInfo) {
        super("Bank Management System");
        this.loginInfo = loginInfo;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);

        initBaseUI();
        bindBaseUI();

        setLocationRelativeTo(null);
    }

    /**
     * Initialize ui elements
     */
    private void initBaseUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.white);

        titleLabel = new StyledLabel("Bank Management System");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        titleLabel.setBounds(10, 5, 500, 35);

        nameLabel = new StyledLabel("Logged in as: " + loginInfo.name);
        nameLabel.setBounds(10, 35, 500, 20);

        logoutButton = new StyledButton("Logout");
        logoutButton.setBounds(670, 16, 100, 35);

        backButton = new StyledButton("Back");
        backButton.setBounds(560, 16, 100, 35);
        backButton.setVisible(false);

        separator.setBounds(0, 65, 1024, 5);
        separator.setForeground(new Color(0x2d9f98));

        mainPanel.add(titleLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(logoutButton);
        mainPanel.add(backButton);
        mainPanel.add(separator);
        add(mainPanel);
    }

    /**
     * Bind event listeners
     */
    private void bindBaseUI() {
        logoutButton.addActionListener(this);
    }

    public void setPageTitle(String title) {
        titleLabel.setText(title);
    }

    /**
     * Logout user
     */
    private void logout() {
        // Close all database connections
        ConnectionManager.getInstance().close();

        new LoginUI().setVisible(true);
        setVisible(false);
        dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            logout();
        }
    }
}
