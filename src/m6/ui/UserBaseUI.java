package m6.ui;

import m6.ConnectionManager;
import m6.UserLoginInfo;
import m6.ui.LoginUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is responsible for showing the top title, name and logout button in
 * all the other pages. It also contains common functions like the back button.
 *
 * So any user related UI must extend this class.
 */
public class UserBaseUI extends JFrame implements ActionListener {

    public JPanel mainPanel;
    private JLabel titleLabel, nameLabel, extraInfoLabel;
    private JButton logoutButton;
    public JButton backButton;
    private UserLoginInfo loginInfo;
    private JSeparator separator = new JSeparator();

    public UserBaseUI(UserLoginInfo loginInfo) {
        super("Bank Management System");
        this.loginInfo = loginInfo;

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);

        initBaseUI();
        bindBaseUI();
    }

    /**
     * Initialize ui elements
     */
    private void initBaseUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        titleLabel = new JLabel("Bank Management System");
        titleLabel.setFont(new Font("Courier", Font.BOLD, 16));
        titleLabel.setBounds(10, 0, 500, 35);

        nameLabel = new JLabel("Name: " + loginInfo.name);
        nameLabel.setFont(new Font("Courier", Font.PLAIN, 12));
        nameLabel.setBounds(10, 35, 200, 20);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(670, 16, 100, 30);

        backButton = new JButton("Back");
        backButton.setBounds(560, 16, 100, 30);
        backButton.setVisible(false);

        separator.setBounds(0, 65, 800, 5);

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
